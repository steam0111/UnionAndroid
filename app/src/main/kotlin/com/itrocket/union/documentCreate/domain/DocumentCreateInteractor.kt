package com.itrocket.union.documentCreate.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.toCreateSyncEntity
import com.itrocket.union.documents.domain.entity.toUpdateSyncEntity
import com.itrocket.union.employeeDetail.domain.EmployeeDetailInteractor
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.structural.domain.dependencies.StructuralRepository
import kotlinx.coroutines.withContext

class DocumentCreateInteractor(
    private val documentRepository: DocumentRepository,
    private val accountingObjectRepository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers,
    private val authMainInteractor: AuthMainInteractor,
    private val structuralRepository: StructuralRepository,
    private val employeeInteractor: EmployeeDetailInteractor
) {

    suspend fun getDocumentById(
        id: String
    ): DocumentDomain {
        return withContext(coreDispatchers.io) {
            documentRepository.getDocumentById(id)
        }
    }

    suspend fun createOrUpdateDocument(
        document: DocumentDomain,
        accountingObjects: List<AccountingObjectDomain>,
        reserves: List<ReservesDomain>,
        params: List<ParamDomain>,
        status: DocumentStatus
    ): String {
        return withContext(coreDispatchers.io) {
            if (!document.isDocumentExists) {
                documentRepository.createDocument(
                    document.copy(
                        accountingObjects = accountingObjects,
                        params = params,
                        documentStatus = status,
                        documentStatusId = status.name,
                        reserves = reserves,
                        userInserted = authMainInteractor.getLogin(),
                        userUpdated = authMainInteractor.getLogin()
                    ).toCreateSyncEntity()
                )
            } else {
                documentRepository.updateDocument(
                    document.copy(
                        accountingObjects = accountingObjects,
                        params = params,
                        documentStatus = status,
                        documentStatusId = status.name,
                        reserves = reserves,
                        userUpdated = authMainInteractor.getLogin()
                    ).toUpdateSyncEntity()
                )
                document.id.orEmpty()
            }
        }
    }

    suspend fun changeParams(
        params: List<ParamDomain>,
        newParams: List<ParamDomain>
    ): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        mutableParams.forEachIndexed { index, paramDomain ->
            val newParam = newParams.find { it.type == paramDomain.type }
            if (newParam != null) {
                mutableParams[index] = newParam
            }
        }

        val oldEmployee = params.find { it.type == ManualType.MOL_IN_STRUCTURAL }
        val newEmployee = newParams.find { it.type == ManualType.MOL_IN_STRUCTURAL }

        if (oldEmployee?.id != newEmployee?.id) {
            val oldStructuralTo = mutableParams.firstOrNull { it.type == ManualType.STRUCTURAL_TO }
            val indexOfStructuralTo = mutableParams.indexOf(oldStructuralTo)
            if (indexOfStructuralTo >= 0) {
                val newStructuralTo = (oldStructuralTo as? StructuralParamDomain)?.let {
                    employeeInteractor.getEmployeeStructuralById(newEmployee?.id, it)
                }

                mutableParams[indexOfStructuralTo] =
                    newStructuralTo ?: oldStructuralTo!!.toInitialState()
            }
        }

        return mutableParams
    }

    fun changeLocation(
        params: List<ParamDomain>,
        location: LocationParamDomain
    ): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val locationIndex = params.indexOfFirst { it.type == location.manualType }
        mutableParams[locationIndex] = location.copy(filtered = false)
        return mutableParams
    }

    suspend fun changeStructural(
        params: List<ParamDomain>,
        structural: StructuralParamDomain
    ): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val structuralIndex = params.indexOfFirst { it.type == structural.manualType }
        mutableParams[structuralIndex] = structural.copy(filtered = false)

        changeBalanceUnit(mutableParams, structural)

        if (structural.type == ManualType.STRUCTURAL_TO) {
            val indexOfMolInStructuralTo =
                mutableParams.indexOfFirst { it.type == ManualType.MOL_IN_STRUCTURAL }
            if (indexOfMolInStructuralTo >= 0) {
                mutableParams[indexOfMolInStructuralTo] =
                    mutableParams[indexOfMolInStructuralTo].toInitialState()
            }
        }

        return mutableParams
    }

    private suspend fun changeBalanceUnit(
        mutableParams: MutableList<ParamDomain>,
        structural: StructuralParamDomain
    ) {
        val balanceUnitType = if (structural.manualType == ManualType.STRUCTURAL_TO) {
            ManualType.BALANCE_UNIT_TO
        } else {
            ManualType.BALANCE_UNIT_FROM
        }
        val index = mutableParams.indexOfFirst { it.type == balanceUnitType }
        if (index != NO_POSITION) {
            val oldBalanceUnitTo = mutableParams[index]
            val newBalanceUnitToPath = structuralRepository.getBalanceUnitFullPath(
                structural.id,
                mutableListOf()
            )
            mutableParams[index] =
                (oldBalanceUnitTo as StructuralParamDomain).copy(structurals = newBalanceUnitToPath.orEmpty())
        }
    }

    fun clearParam(list: List<ParamDomain>, param: ParamDomain): List<ParamDomain> {
        val mutableList = list.toMutableList()
        val currentIndex = mutableList.indexOfFirst { it.type == param.type }
        mutableList[currentIndex] = mutableList[currentIndex].toInitialState()

        when (param.type) {
            ManualType.STRUCTURAL_TO -> {
                val balanceUnitToIndex =
                    mutableList.indexOfFirst { it.type == ManualType.BALANCE_UNIT_TO }
                if (balanceUnitToIndex >= 0) {
                    mutableList[balanceUnitToIndex] =
                        mutableList[balanceUnitToIndex].toInitialState()
                }

                val molIndex = mutableList.indexOfFirst { it.type == ManualType.MOL_IN_STRUCTURAL }
                if (molIndex >= 0) {
                    mutableList[molIndex] = mutableList[molIndex].toInitialState()
                }
            }
            ManualType.STRUCTURAL_FROM -> {
                val balanceUnitFromIndex =
                    mutableList.indexOfFirst { it.type == ManualType.BALANCE_UNIT_FROM }
                if (balanceUnitFromIndex >= 0) {
                    mutableList[balanceUnitFromIndex] =
                        mutableList[balanceUnitFromIndex].toInitialState()
                }
            }
        }
        return mutableList
    }

    fun clearParams(list: List<ParamDomain>): List<ParamDomain> {
        return list.map {
            it.toInitialState()
        }
    }

    fun getAccountingObjectIds(accountingObjects: List<AccountingObjectDomain>): List<String> =
        accountingObjects.map { it.id }

    fun getReservesIds(reserves: List<ReservesDomain>): List<String> =
        reserves.map { it.id }

    fun addAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        accountingObject: AccountingObjectDomain
    ): List<AccountingObjectDomain> {
        val mutableList = accountingObjects.toMutableList()
        mutableList.add(accountingObject)
        return mutableList
    }

    fun updateReserveCount(
        reserves: List<ReservesDomain>,
        id: String,
        count: Long
    ): List<ReservesDomain> {
        val mutableList = reserves.toMutableList()
        val index = mutableList.indexOfFirst { it.id == id }
        val reserve = mutableList[index]
        mutableList[index] = reserve.copy(itemsCount = count)
        return mutableList
    }

    fun addReserve(
        reserves: List<ReservesDomain>,
        reserve: ReservesDomain
    ): List<ReservesDomain> {
        val mutableList = reserves.toMutableList()
        mutableList.add(reserve)
        return mutableList
    }

    suspend fun handleNewAccountingObjectRfids(
        accountingObjects: List<AccountingObjectDomain>,
        handledAccountingObjectRfid: String
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectRfids = mutableListOf<String>()

            val index =
                accountingObjects.indexOfFirst { it.rfidValue == handledAccountingObjectRfid }
            if (index == NO_POSITION) {
                newAccountingObjectRfids.add(handledAccountingObjectRfid)
            }
            val newAccountingObjects =
                accountingObjectRepository.getAccountingObjectsByRfids(newAccountingObjectRfids)
            newAccountingObjects + accountingObjects
        }
    }

    suspend fun handleNewAccountingObjectBarcode(
        accountingObjects: List<AccountingObjectDomain>,
        barcode: String,
        isSerialNumber: Boolean
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectBarcode = mutableListOf<AccountingObjectDomain>()

            val index = accountingObjects.indexOfFirst {
                if (isSerialNumber) {
                    it.factoryNumber == barcode
                } else {
                    it.barcodeValue == barcode
                }
            }
            if (index == NO_POSITION) {
                val barcodeAccountingObject = if (isSerialNumber) {
                    accountingObjectRepository.getAccountingObjectsByBarcode(
                        barcode = null,
                        serialNumber = barcode
                    )
                } else {
                    accountingObjectRepository.getAccountingObjectsByBarcode(
                        barcode = barcode,
                        serialNumber = null
                    )
                }
                barcodeAccountingObject?.let {
                    newAccountingObjectBarcode.add(it)
                }
            }
            newAccountingObjectBarcode + accountingObjects
        }
    }

    fun completeDocument(
        params: List<ParamDomain>,
        accountingObjects: List<AccountingObjectDomain>,
        reserves: List<ReservesDomain>,
        document: DocumentDomain
    ): DocumentDomain {
        return document.copy(
            params = params,
            accountingObjects = accountingObjects,
            reserves = reserves
        )
    }

    suspend fun getFilterParams(params: List<ParamDomain>) = withContext(coreDispatchers.io) {
        params.filter { it.isFilter }
    }

    companion object {
        private const val NO_POSITION = -1
    }
}