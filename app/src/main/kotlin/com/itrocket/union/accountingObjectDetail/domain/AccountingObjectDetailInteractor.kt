package com.itrocket.union.accountingObjectDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoType
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.image.domain.ImageDomain
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AccountingObjectDetailInteractor(
    private val repository: AccountingObjectDetailRepository,
    private val accountingObjectRepository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers,
    private val unionPermissionsInteractor: UnionPermissionsInteractor,
    private val authMainInteractor: AuthMainInteractor
) {

    suspend fun getAccountingObject(id: String): AccountingObjectDomain =
        withContext(coreDispatchers.io) {
            repository.getAccountingObject(id)
        }

    suspend fun getAccountingObjectByParams(
        rfid: String? = null,
        barcode: String? = null,
        factoryNumber: String? = null
    ): AccountingObjectDomain = withContext(coreDispatchers.io) {
        repository.getAccountingObjectByParams(
            rfid = rfid,
            barcode = barcode,
            factoryNumber = factoryNumber
        )
    }

    suspend fun saveImage(imageDomain: ImageDomain, accountingObjectId: String) {
        repository.saveImage(
            imageDomain = imageDomain,
            accountingObjectId = accountingObjectId,
            userInserted = authMainInteractor.getLogin()
        )
    }

    suspend fun getAccountingObjectImagesFlow(accountingObjectId: String) =
        repository.getAccountingObjectImagesFlow(accountingObjectId)

    suspend fun getAccountingObjectFlow(id: String): Flow<AccountingObjectDomain> =
        repository.getAccountingObjectFlow(id).map {
            val additionalInfo = checkAdditionalListPermissions(it.listAdditionallyInfo)
            it.copy(listMainInfo = it.listMainInfo, listAdditionallyInfo = additionalInfo)
        }

    private suspend fun checkAdditionalListPermissions(listInfo: List<ObjectInfoDomain>): List<ObjectInfoDomain> {
        var mainInfo = listInfo
        val canShowSimpleFields =
            unionPermissionsInteractor.canRead(UnionPermission.ACCOUNTING_OBJECT_SIMPLE_ADDITIONAL_FIELD)
        val canShowVocabularyFields =
            unionPermissionsInteractor.canRead(UnionPermission.ACCOUNTING_OBJECT_VOCABULARY_ADDITIONAL_FIELD)
        if (!canShowSimpleFields) {
            mainInfo =
                mainInfo.filter { it.filedType != ObjectInfoType.SIMPLE_ADDITIONAL_FIELD }
        }
        if (!canShowVocabularyFields) {
            mainInfo =
                mainInfo.filter { it.filedType != ObjectInfoType.VOCABULARY_ADDITIONAL_FIELD }
        }
        return mainInfo
    }

    suspend fun updateLabelType(
        accountingObjectDomain: AccountingObjectDomain,
        labelTypeId: String
    ) = withContext(coreDispatchers.io) {
        repository.updateLabelType(
            accountingObject = accountingObjectDomain,
            labelTypeId = labelTypeId
        )
    }

    suspend fun updateAccountingObjectMarked(accountingObjectId: String, rfid: String) {
        withContext(coreDispatchers.io) {
            repository.updateAccountingObjectMarked(accountingObjectId, rfid)
        }
    }

    suspend fun updateScanningData(accountingObjectDomain: AccountingObjectDomain) =
        withContext(coreDispatchers.io) {
            repository.updateScanningData(accountingObjectDomain)
        }

    suspend fun removeRfid(accountingObjectDomain: AccountingObjectDomain) =
        withContext(coreDispatchers.io) {
            repository.updateScanningData(accountingObjectDomain.copy(rfidValue = null))
        }

    suspend fun removeBarcode(accountingObjectDomain: AccountingObjectDomain) =
        withContext(coreDispatchers.io) {
            repository.updateScanningData(accountingObjectDomain.copy(barcodeValue = null))
        }

    suspend fun generateRfid(
        accountingObjectDomain: AccountingObjectDomain,
    ) {
        return withContext(coreDispatchers.io) {
            val rawRfid = UUID.randomUUID().toString()
            val rfid = rawRfid.toByteArray().toHexString().take(RFID_SIZE).uppercase()
            repository.updateScanningData(accountingObjectDomain.copy(rfidValue = rfid))
        }
    }

    suspend fun writeOffAccountingObject(accountingObjectDomain: AccountingObjectDomain) {
        repository.writeOffAccountingObject(accountingObjectDomain)
    }

    suspend fun getAccountingObjectImages(accountingObjectId: String) =
        repository.getAccountingObjectImages(accountingObjectId)

    private fun ByteArray.toHexString() = joinToString("") {
        String.format("%02x", it)
    }

    companion object {
        const val RFID_SIZE = 24
    }
}