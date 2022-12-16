package com.itrocket.union.identify.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.sgtin.SgtinFormatter
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.labelTypeDetail.domain.dependencies.LabelTypeDetailRepository
import com.itrocket.union.nomenclature.domain.NomenclatureInteractor
import com.itrocket.union.nomenclatureDetail.domain.NomenclatureDetailInteractor
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.coroutines.withContext

class IdentifyInteractor(
    private val accountingObjectRepository: AccountingObjectRepository,
    private val reservesRepository: ReservesRepository,
    private val coreDispatchers: CoreDispatchers,
    private val nomenclatureDetailInteractor: NomenclatureDetailInteractor,
    private val labelTypeDetailRepository: LabelTypeDetailRepository,
    private val sgtinFormatter: SgtinFormatter
) {
    fun addAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        accountingObject: AccountingObjectDomain
    ): List<AccountingObjectDomain> {
        val mutableList = accountingObjects.toMutableList()
        mutableList.add(accountingObject)
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
                val barcodeAccountingObject =
                    if (isSerialNumber) {
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

    suspend fun handleNewNomenclatureReserveRfids(
        nomenclatureReserves: List<NomenclatureReserveDomain>,
        rfids: List<String>,
        oldsRfids: List<String>
    ): NomenclatureReserveRfid {
        return withContext(coreDispatchers.io) {
            var newNomenclatureReserves = nomenclatureReserves
            val newRfids = mutableListOf<String>()
            rfids.filter { !oldsRfids.contains(it) }.forEach {
                newRfids.add(it)
                val barcode = sgtinFormatter.epcRfidToBarcode(it).barcode
                newNomenclatureReserves = handleNewNomenclatureReserveBarcode(
                    barcode = barcode,
                    nomenclatureReserves = newNomenclatureReserves
                )
            }
            NomenclatureReserveRfid(
                newNomenclatureReserves = newNomenclatureReserves,
                newRfids = newRfids
            )
        }
    }

    suspend fun handleNewNomenclatureReserveBarcode(
        nomenclatureReserves: List<NomenclatureReserveDomain>,
        barcode: String,
    ): List<NomenclatureReserveDomain> {
        return withContext(coreDispatchers.io) {
            val reserves = reservesRepository.getReserves(
                barcode = barcode,
                offset = null,
                structuralIds = null,
                limit = null,
                selectedLocationIds = null,
                hideZeroReserves = false
            )

            if (reserves.isEmpty()) {
                nomenclatureReserves
            } else {
                val reserve = reserves.first()
                val existNomenclature = nomenclatureReserves.find {
                    it.nomenclatureId == reserve.nomenclatureId && it.labelTypeId == reserve.labelTypeId && it.consignment == reserve.consignment
                }
                resolveNomenclatureReserves(
                    reserve = reserve,
                    existNomenclature = existNomenclature,
                    nomenclatureReserves = nomenclatureReserves
                )
            }
        }
    }

    private suspend fun resolveNomenclatureReserves(
        reserve: ReservesDomain,
        existNomenclature: NomenclatureReserveDomain?,
        nomenclatureReserves: List<NomenclatureReserveDomain>,
    ): List<NomenclatureReserveDomain> {
        val newNomenclatures = nomenclatureReserves.toMutableList()
        when {
            existNomenclature != null -> {
                val index = newNomenclatures.indexOfFirst { it == existNomenclature }
                newNomenclatures[index] =
                    existNomenclature.copy(count = existNomenclature.count + 1)
            }
            reserve.nomenclatureId == null -> {
                //nothing
            }
            else -> {

                val nomenclature =
                    nomenclatureDetailInteractor.getNomenclatureDetail(reserve.nomenclatureId)
                val labelType = if (reserve.labelTypeId != null) {
                    labelTypeDetailRepository.getLabelTypeById(reserve.labelTypeId)
                } else {
                    null
                }

                val newNomenclatureReserve = NomenclatureReserveDomain(
                    nomenclatureId = reserve.nomenclatureId,
                    nomenclature = nomenclature.name,
                    labelType = labelType?.name,
                    labelTypeId = reserve.labelTypeId,
                    consignment = reserve.consignment,
                    count = 1
                )
                newNomenclatures.add(newNomenclatureReserve)
            }
        }
        return newNomenclatures
    }

    suspend fun writeOffAccountingObjects(accountingObjects: List<AccountingObjectDomain>) {
        return accountingObjectRepository.writeOffAccountingObjects(accountingObjects)
    }

    companion object {
        const val ACCOUNTING_OBJECT_PAGE = 0
        const val NOMENCLATURE_RESERVE_PAGE = 1
        private const val NO_POSITION = -1
    }
}