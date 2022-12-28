package com.itrocket.union.documentCreate.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.toCreateSyncEntity
import com.itrocket.union.documents.domain.entity.toUpdateSyncEntity
import com.itrocket.union.employeeDetail.domain.EmployeeDetailInteractor
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.structural.domain.dependencies.StructuralRepository
import kotlinx.coroutines.withContext
import java.math.BigDecimal

/**
 * Создание
 * Детальное описание
 * Редактирование документа
 */
class DocumentCreateInteractor(
    private val documentRepository: DocumentRepository,
    private val accountingObjectRepository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers,
    private val authMainInteractor: AuthMainInteractor,
    private val structuralRepository: StructuralRepository,
    private val employeeInteractor: EmployeeDetailInteractor
) {

    private suspend fun currentUserId(): String? = authMainInteractor.getMyConfig().employeeId

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
        oldParams: List<ParamDomain>,
        newParams: List<ParamDomain>,
        documentType: DocumentTypeDomain
    ): List<ParamDomain> {
        return when (documentType) {
            DocumentTypeDomain.GIVE -> updateGiveFilter(oldParams, newParams)
            DocumentTypeDomain.RETURN -> updateReturnFilter(oldParams, newParams)
            DocumentTypeDomain.RELOCATION -> updateRelocationFilter(oldParams, newParams)
            else -> updateOtherScreenParams(oldParams, newParams)
        }
    }

    private suspend fun updateRelocationFilter(
        oldParams: List<ParamDomain>,
        newParams: List<ParamDomain>,
    ): List<ParamDomain> {
        val mutableParams: MutableMap<ManualType, ParamDomain> =
            oldParams.associateBy { it.type }.toMutableMap()

        val newParamsMap = newParams.associateBy { it.type }

        mutableParams.forEach { (type, paramDomain) ->
            val newParam = newParamsMap[paramDomain.type]
            if (newParam != null) {
                mutableParams[type] = newParam
            }

            if (newParam?.type == ManualType.STRUCTURAL_FROM) {
                if ((paramDomain as StructuralParamDomain).needUpdate) {
                    changeStructuralByEmployee(
                        mutableParams = mutableParams,
                        structuralType = ManualType.STRUCTURAL_FROM,
                        employeeId = currentUserId(),
                        needUpdate = false
                    )
                }
            } else if (newParam?.type == ManualType.MOL_IN_STRUCTURAL) {
                changeStructuralByEmployee(
                    mutableParams = mutableParams,
                    structuralType = ManualType.STRUCTURAL_TO,
                    employeeId = newParam.id
                )
            }
            updateBalanceUnits(mutableParams)
        }
        return mutableParams.values.toList()
    }

    private suspend fun updateReturnFilter(
        oldParams: List<ParamDomain>,
        newParams: List<ParamDomain>,
    ): List<ParamDomain> {
        val mutableParams: MutableMap<ManualType, ParamDomain> =
            oldParams.associateBy { it.type }.toMutableMap()
        val newParamsMap = newParams.associateBy { it.type }

        mutableParams.forEach { (type, paramDomain) ->
            val newParam = newParamsMap[paramDomain.type]
            if (newParam != null) {
                mutableParams[type] = newParam
            }

            if (newParam?.type == ManualType.STRUCTURAL_TO) {
                if ((paramDomain as StructuralParamDomain).needUpdate) {
                    changeStructuralByEmployee(
                        mutableParams = mutableParams,
                        structuralType = ManualType.STRUCTURAL_TO,
                        employeeId = currentUserId(),
                        needUpdate = false
                    )
                }
            }
            updateBalanceUnits(mutableParams)
        }
        return mutableParams.values.toList()
    }

    private suspend fun updateGiveFilter(
        oldParams: List<ParamDomain>,
        newParams: List<ParamDomain>,
    ): List<ParamDomain> {
        val mutableParams: MutableMap<ManualType, ParamDomain> =
            oldParams.associateBy { it.type }.toMutableMap()

        val newParamsMap = newParams.associateBy { it.type }

        mutableParams.forEach { (type, paramDomain) ->
            val newParam = newParamsMap[type]
            if (newParam != null) {
                mutableParams[type] = newParam
            }

            if (newParam?.type == ManualType.STRUCTURAL_FROM) {
                if ((paramDomain as StructuralParamDomain).needUpdate) {
                    changeStructuralByEmployee(
                        mutableParams = mutableParams,
                        structuralType = ManualType.STRUCTURAL_FROM,
                        employeeId = currentUserId(),
                        needUpdate = false
                    )
                }
            } else if (newParam?.type == ManualType.EXPLOITING) {
                changeStructuralByEmployee(
                    mutableParams = mutableParams,
                    structuralType = ManualType.STRUCTURAL_TO,
                    employeeId = newParam.id
                )
            }
            updateBalanceUnits(mutableParams)
        }
        return mutableParams.values.toList()
    }

    private fun updateOtherScreenParams(
        oldParams: List<ParamDomain>,
        newParams: List<ParamDomain>,
    ): List<ParamDomain> {
        val mutableParams: MutableMap<ManualType, ParamDomain> =
            oldParams.associateBy { it.type }.toMutableMap()

        val newParamsMap = newParams.associateBy { it.type }

        mutableParams.forEach { (type, _) ->
            val newParam = newParamsMap[type]
            if (newParam != null) {
                mutableParams[type] = newParam
            }
        }
        return mutableParams.values.toList()
    }

    private suspend fun changeStructuralByEmployee(
        mutableParams: MutableMap<ManualType, ParamDomain>,
        structuralType: ManualType,
        employeeId: String?,
        needUpdate: Boolean = true
    ) {
        if (employeeId == null) return
        val structural = mutableParams[structuralType] as? StructuralParamDomain ?: return
        val newStructural = employeeInteractor.getEmployeeStructuralById(
            employeeId,
            structural,
            needUpdate
        )
        mutableParams[structuralType] = newStructural
    }

    private suspend fun updateBalanceUnits(
        mutableParams: MutableMap<ManualType, ParamDomain>
    ) {
        changeBalanceUnit(
            mutableParams,
            mutableParams[ManualType.STRUCTURAL_TO] as? StructuralParamDomain
        )
        changeBalanceUnit(
            mutableParams,
            mutableParams[ManualType.STRUCTURAL_FROM] as? StructuralParamDomain
        )
    }

    private suspend fun changeBalanceUnit(
        mutableParams: MutableMap<ManualType, ParamDomain>,
        structural: StructuralParamDomain?
    ) {
        val balanceUnitType = when (structural?.manualType) {
            ManualType.STRUCTURAL_TO -> ManualType.BALANCE_UNIT_TO
            ManualType.STRUCTURAL_FROM -> ManualType.BALANCE_UNIT_FROM
            else -> return
        }

        val oldBalanceUnitTo = mutableParams[balanceUnitType] ?: return
        val newBalanceUnitToPath = structuralRepository.getBalanceUnitFullPath(
            structural.id,
            mutableListOf()
        )
        mutableParams[balanceUnitType] =
            (oldBalanceUnitTo as StructuralParamDomain).copy(structurals = newBalanceUnitToPath.orEmpty())
    }

    suspend fun clearParam(
        list: List<ParamDomain>,
        param: ParamDomain,
        documentType: DocumentTypeDomain
    ): List<ParamDomain> {
        val mutableParams: MutableMap<ManualType, ParamDomain> =
            list.associateBy { it.type }.toMutableMap()
        mutableParams[param.type] = mutableParams[param.type]?.toInitialState() ?: return list

        when (param.type) {
            ManualType.STRUCTURAL_TO -> {
                if (documentType == DocumentTypeDomain.GIVE) {
                    clearParamToInitialState(mutableParams, ManualType.EXPLOITING)
                } else if (documentType == DocumentTypeDomain.RELOCATION) {
                    clearParamToInitialState(mutableParams, ManualType.BALANCE_UNIT_TO)
                    clearParamToInitialState(mutableParams, ManualType.MOL_IN_STRUCTURAL)
                }
            }
            ManualType.STRUCTURAL_FROM -> {
                clearParamToInitialState(mutableParams, ManualType.BALANCE_UNIT_FROM)
            }
            ManualType.EXPLOITING -> {
                if (documentType == DocumentTypeDomain.GIVE) {
                    clearParamToInitialState(mutableParams, ManualType.STRUCTURAL_TO)
                }
            }
            ManualType.MOL_IN_STRUCTURAL -> {
                if (documentType == DocumentTypeDomain.RELOCATION) {
                    clearParamToInitialState(mutableParams, ManualType.STRUCTURAL_TO)
                }
            }
        }
        updateBalanceUnits(mutableParams)
        return mutableParams.values.toList()
    }

    private fun clearParamToInitialState(
        mutableParams: MutableMap<ManualType, ParamDomain>,
        type: ManualType
    ) {
        val param = mutableParams[type]
        if (param != null) {
            mutableParams[type] = param.toInitialState()
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
        count: BigDecimal
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
        handledAccountingObjectRfids: List<String>
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectRfids = mutableListOf<String>()
            val existAccountingObjectRfids = hashMapOf<String, String>()

            accountingObjects.forEach {
                if (it.rfidValue != null) {
                    existAccountingObjectRfids[it.rfidValue] = it.id
                }
            }

            handledAccountingObjectRfids.forEach { rfid ->
                val isExist = existAccountingObjectRfids[rfid] != null
                if (!isExist) {
                    newAccountingObjectRfids.add(rfid)
                }
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

    fun deleteAccountingObject(
        accountingObjectId: String,
        accountingObjectList: List<AccountingObjectDomain>
    ): List<AccountingObjectDomain> {
        val mutableList = accountingObjectList.toMutableList()
        val accountingObjectIndex = mutableList.indexOfFirst { it.id == accountingObjectId }
        if (accountingObjectIndex >= 0) {
            mutableList.removeAt(accountingObjectIndex)
        }
        return mutableList
    }

    fun deleteReserve(
        reserveId: String,
        reserveList: List<ReservesDomain>
    ): List<ReservesDomain> {
        val mutableList = reserveList.toMutableList()
        val reserveIndex = mutableList.indexOfFirst { it.id == reserveId }
        if (reserveIndex >= 0) {
            mutableList.removeAt(reserveIndex)
        }
        return mutableList
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

    suspend fun isNotAllAccountingObjectMarked(accountingObjects: List<AccountingObjectDomain>): Boolean {
        return withContext(coreDispatchers.io) {
            accountingObjects.any { !it.marked }
        }
    }

    suspend fun filterNotMarkedAccountingObjectNames(accountingObjects: List<AccountingObjectDomain>): List<String> {
        return withContext(coreDispatchers.io) {
            accountingObjects.filter { !it.marked }.map { it.title }
        }
    }

    suspend fun getExploitingFilterIfDocumentReturn(
        documentType: DocumentTypeDomain,
        params: List<ParamDomain>
    ): List<ParamDomain> {
        return withContext(coreDispatchers.io) {
            if (documentType != DocumentTypeDomain.RETURN) {
                listOf()
            } else {
                val exploitingIndex = params.indexOfFirst { it.type == ManualType.EXPLOITING }
                listOf(params[exploitingIndex])
            }
        }
    }

    suspend fun tryChangeReturnExploiting(
        documentType: DocumentTypeDomain,
        params: List<ParamDomain>,
        accountingObjects: List<AccountingObjectDomain>
    ): List<ParamDomain> {
        return withContext(coreDispatchers.io) {
            val isDocumentReturn = documentType == DocumentTypeDomain.RETURN

            if (isDocumentReturn) {
                changeReturnExploiting(params = params, accountingObjects = accountingObjects)
            } else {
                params
            }
        }
    }

    private suspend fun changeReturnExploiting(
        params: List<ParamDomain>,
        accountingObjects: List<AccountingObjectDomain>
    ): List<ParamDomain> {
        return withContext(coreDispatchers.io) {
            val mutableParams = params.toMutableList()
            val paramExploitingIndex =
                mutableParams.indexOfFirst { it.type == ManualType.EXPLOITING }
            val paramExploiting = mutableParams[paramExploitingIndex]

            if (paramExploiting.value.isEmpty()) {
                val accountingObjectExploitingId =
                    accountingObjects.find { it.exploitingId != null }?.exploitingId
                accountingObjectExploitingId?.let {
                    val exploiting = employeeInteractor.getEmployeeDetail(it)
                    mutableParams[paramExploitingIndex] =
                        mutableParams[paramExploitingIndex].copy(
                            id = exploiting?.id,
                            value = exploiting?.fullName.orEmpty()
                        )
                }
            }
            mutableParams
        }
    }

    companion object {
        private const val NO_POSITION = -1
    }
}