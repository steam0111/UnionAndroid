package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectAdditionalFieldsSyncApi
import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.data.EnumsSyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectScanningData
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.AccountingObjectUpdateSyncEntity
import com.example.union_sync_api.entity.EnumType
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.sqlAccountingObjectDetailQuery
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.data.mapper.toAccountingObjectDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toAccountingObjectScanningUpdate
import com.example.union_sync_impl.data.mapper.toAccountingObjectUpdate
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountingObjectSyncApiImpl(
    private val accountingObjectsDao: AccountingObjectDao,
    private val locationSyncApi: LocationSyncApi,
    private val structuralSyncApi: StructuralSyncApi,
    private val accountingObjectAdditionalFieldsSyncApi: AccountingObjectAdditionalFieldsSyncApi,
    private val enumsApi: EnumsSyncApi
) : AccountingObjectSyncApi {

    override suspend fun getAccountingObjects(
        exploitingId: String?,
        molId: String?,
        producerId: String?,
        equipmentTypeId: String?,
        providerId: String?,
        rfids: List<String>?,
        barcode: String?,
        statusId: String?,
        textQuery: String?,
        accountingObjectsIds: List<String>?,
        locationIds: List<String?>?,
        structuralId: List<String?>?,
        offset: Long?,
        limit: Long?,
        serialNumber: String?
    ): List<AccountingObjectSyncEntity> {
        return accountingObjectsDao.getAll(
            sqlAccountingObjectQuery(
                exploitingId = exploitingId,
                producerId = producerId,
                providerId = providerId,
                equipmentTypeId = equipmentTypeId,
                molId = molId,
                rfids = rfids,
                barcode = barcode,
                statusId = statusId,
                accountingObjectsIds = accountingObjectsIds,
                textQuery = textQuery,
                locationIds = locationIds,
                structuralIds = structuralId,
                offset = offset,
                limit = limit
            )
        ).map {
            val location = locationSyncApi.getLocationById(it.accountingObjectDb.locationId)
            it.toSyncEntity(locationSyncEntity = listOfNotNull(location))
        }
    }

    override suspend fun getAccountingObjectsCount(
        exploitingId: String?,
        molId: String?,
        producerId: String?,
        equipmentTypeId: String?,
        providerId: String?,
        rfids: List<String>?,
        barcode: String?,
        statusId: String?,
        textQuery: String?,
        accountingObjectsIds: List<String>?,
        locationIds: List<String?>?,
        structuralIds: List<String?>?
    ): Long {
        return accountingObjectsDao.getCount(
            sqlAccountingObjectQuery(
                exploitingId = exploitingId,
                producerId = producerId,
                providerId = providerId,
                equipmentTypeId = equipmentTypeId,
                molId = molId,
                rfids = rfids,
                barcode = barcode,
                statusId = statusId,
                accountingObjectsIds = accountingObjectsIds,
                textQuery = textQuery,
                isFilterCount = true,
                locationIds = locationIds,
                structuralIds = structuralIds
            )
        )
    }

    override suspend fun getAccountingObjectDetailById(id: String): AccountingObjectDetailSyncEntity {
        val fullAccountingObjectDb = accountingObjectsDao.getById(id)
        val location =
            locationSyncApi.getLocationFullPath(fullAccountingObjectDb.accountingObjectDb.locationId)

        val structurals =
            structuralSyncApi.getStructuralFullPath(
                fullAccountingObjectDb.structuralDb?.id,
                mutableListOf()
            ).orEmpty()
        val balanceUnitIndex = structurals.indexOfLast { it.balanceUnit }.takeIf { it >= 0 } ?: 0
        val balanceUnit = structurals.subList(0, balanceUnitIndex)
        val categorySyncEntity = enumsApi.getByCompoundId(
            enumType = EnumType.ACCOUNTING_CATEGORY,
            id = fullAccountingObjectDb.accountingObjectDb.accountingObjectCategoryId
        )

        return fullAccountingObjectDb.toAccountingObjectDetailSyncEntity(
            location,
            balanceUnit,
            structurals,
            simpleAdditionalFields = accountingObjectAdditionalFieldsSyncApi.getSimpleAdditionalFields(
                fullAccountingObjectDb.accountingObjectDb.id
            ),
            vocabularyAdditionalFields = accountingObjectAdditionalFieldsSyncApi.getVocabularyAdditionalFields(
                fullAccountingObjectDb.accountingObjectDb.id
            ),
            categorySyncEntity = categorySyncEntity
        )
    }

    override suspend fun getAccountingObjectDetailByParams(
        rfid: String?,
        barcode: String?,
        factoryNumber: String?
    ): AccountingObjectDetailSyncEntity {
        val fullAccountingObject = accountingObjectsDao.getById(
            sqlAccountingObjectDetailQuery(
                rfid = rfid,
                barcode = barcode,
                factoryNumber = factoryNumber
            )
        )
        val location =
            locationSyncApi.getLocationFullPath(fullAccountingObject.accountingObjectDb.locationId)

        val structurals =
            structuralSyncApi.getStructuralFullPath(
                fullAccountingObject.structuralDb?.id,
                mutableListOf()
            )
                .orEmpty()
        val balanceUnitIndex = structurals.indexOfLast { it.balanceUnit }
        val balanceUnit = structurals.subList(0, balanceUnitIndex + 1)

        val categorySyncEntity = enumsApi.getByCompoundId(
            enumType = EnumType.ACCOUNTING_CATEGORY,
            id = fullAccountingObject.accountingObjectDb.accountingObjectCategoryId
        )

        return fullAccountingObject.toAccountingObjectDetailSyncEntity(
            location,
            balanceUnit,
            structurals,
            simpleAdditionalFields = accountingObjectAdditionalFieldsSyncApi.getSimpleAdditionalFields(
                fullAccountingObject.accountingObjectDb.id
            ),
            vocabularyAdditionalFields = accountingObjectAdditionalFieldsSyncApi.getVocabularyAdditionalFields(
                fullAccountingObject.accountingObjectDb.id
            ),
            categorySyncEntity = categorySyncEntity
        )
    }

    override suspend fun getAccountingObjectDetailByIdFlow(id: String): Flow<AccountingObjectDetailSyncEntity> {
        return accountingObjectsDao.getByIdFlow(id).map {
            val location =
                locationSyncApi.getLocationFullPath(it.accountingObjectDb.locationId)

            val structurals =
                structuralSyncApi.getStructuralFullPath(it.structuralDb?.id, mutableListOf())
                    .orEmpty()
            val balanceUnitIndex = structurals.indexOfLast { it.balanceUnit }
            val balanceUnit = structurals.subList(0, balanceUnitIndex + 1)

            val categorySyncEntity = enumsApi.getByCompoundId(
                enumType = EnumType.ACCOUNTING_CATEGORY,
                id = it.accountingObjectDb.accountingObjectCategoryId
            )

            it.toAccountingObjectDetailSyncEntity(
                location,
                balanceUnit,
                structurals,
                simpleAdditionalFields = accountingObjectAdditionalFieldsSyncApi.getSimpleAdditionalFields(
                    it.accountingObjectDb.id
                ),
                vocabularyAdditionalFields = accountingObjectAdditionalFieldsSyncApi.getVocabularyAdditionalFields(
                    it.accountingObjectDb.id
                ),
                categorySyncEntity = categorySyncEntity
            )
        }
    }

    override suspend fun updateAccountingObjects(accountingObjects: List<AccountingObjectUpdateSyncEntity>) {
        accountingObjectsDao.update(accountingObjects.map { it.toAccountingObjectUpdate() })
    }

    override suspend fun updateAccountingObjectScanningData(accountingObject: AccountingObjectScanningData) {
        accountingObjectsDao.update(accountingObject.toAccountingObjectScanningUpdate())
    }
}