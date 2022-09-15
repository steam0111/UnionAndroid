package com.itrocket.union.changeScanData.domain

import com.itrocket.union.changeScanData.domain.dependencies.ChangeScanDataRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.domain.AccountingObjectDetailInteractor
import com.itrocket.union.changeScanData.domain.entity.ChangeScanType

class ChangeScanDataInteractor(
    private val repository: ChangeScanDataRepository,
    private val accountingObjectDetailInteractor: AccountingObjectDetailInteractor,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun changeAccountingObjectScanningValue(
        entityId: String,
        scanningValue: String,
        changeScanType: ChangeScanType,
    ) {
        val accountingObject = accountingObjectDetailInteractor.getAccountingObject(entityId)
        val newAccountingObject = when (changeScanType) {
            ChangeScanType.RFID -> accountingObject.copy(rfidValue = scanningValue)
            ChangeScanType.BARCODE -> accountingObject.copy(barcodeValue = scanningValue)
            ChangeScanType.SN -> accountingObject.copy(factoryNumber = scanningValue)
        }
        accountingObjectDetailInteractor.updateScanningData(newAccountingObject)
    }

    suspend fun isScanDataExist(
        changeScanType: ChangeScanType,
        scanData: String,
    ): Boolean {
        return try {
            when (changeScanType) {
                ChangeScanType.RFID -> accountingObjectDetailInteractor.getAccountingObjectByParams(
                    rfid = scanData
                )
                ChangeScanType.BARCODE -> accountingObjectDetailInteractor.getAccountingObjectByParams(
                    barcode = scanData
                )
                ChangeScanType.SN -> accountingObjectDetailInteractor.getAccountingObjectByParams(
                    factoryNumber = scanData
                )
            }
            true

        } catch (t: Throwable) {
            false
        }
    }

}