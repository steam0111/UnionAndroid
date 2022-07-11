package com.example.union_sync_impl.data

import com.example.union_sync_api.data.InventorySyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.entity.AccountingObjectInfoSyncEntity
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.dao.InventoryRecordDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.dao.sqlInventoryQuery
import com.example.union_sync_impl.dao.sqlInventoryRecordQuery
import com.example.union_sync_impl.data.mapper.toInventoryDb
import com.example.union_sync_impl.data.mapper.toInventorySyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.FullAccountingObject
import com.example.union_sync_impl.entity.FullDocument
import com.example.union_sync_impl.entity.FullInventory
import com.example.union_sync_impl.entity.InventoryRecordDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class InventorySyncApiImpl(
    private val inventoryDao: InventoryDao,
    private val locationDao: LocationDao,
    private val accountingObjectDao: AccountingObjectDao,
    private val inventoryRecordDao: InventoryRecordDao,
    private val locationSyncApi: LocationSyncApi
) : InventorySyncApi {
    override suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity): String {
        val inventoryId = UUID.randomUUID().toString()
        inventoryDao.insert(inventoryCreateSyncEntity.toInventoryDb(inventoryId))
        updateRecords(
            inventoryId = inventoryId,
            accountingObjectIds = inventoryCreateSyncEntity.accountingObjectsIds
        )
        return inventoryId
    }

    override suspend fun getInventories(
        textQuery: String?,
        organizationId: String?,
        molId: String?
    ): Flow<List<InventorySyncEntity>> {
        return inventoryDao.getAll(
            sqlInventoryQuery(
                textQuery = textQuery,
                organizationId = organizationId,
                molId = molId
            )
        ).map {
            it.map {
                it.inventoryDb.toInventorySyncEntity(
                    organizationSyncEntity = it.organizationDb?.toSyncEntity(),
                    mol = it.employeeDb?.toSyncEntity(),
                    locationSyncEntities = it.getLocations(),
                    accountingObjects = listOf()
                )
            }
        }
    }

    private suspend fun FullInventory.getLocations(): List<LocationSyncEntity> {
        return inventoryDb.locationIds?.map {
            locationSyncApi.getLocationById(it)
        }.orEmpty()
    }

    override suspend fun getInventoriesCount(
        textQuery: String?,
        organizationId: String?,
        molId: String?
    ): Long {
        return inventoryDao.getCount(
            sqlInventoryQuery(
                textQuery = textQuery,
                organizationId = organizationId,
                molId = molId,
                isFilterCount = true
            )
        )
    }

    override suspend fun getInventoryById(id: String): InventorySyncEntity {
        val fullInventory = inventoryDao.getInventoryById(id)

        val locations = fullInventory.inventoryDb.locationIds?.map { locationId ->
            locationSyncApi.getLocationById(locationId)
        }

        val inventoryRecords =
            inventoryRecordDao.getAll(sqlInventoryRecordQuery(fullInventory.inventoryDb.id))

        val accountingObjectIds = inventoryRecords.map {
            it.accountingObjectId
        }

        val accountingObjects =
            accountingObjectDao.getAll(sqlAccountingObjectQuery(accountingObjectsIds = accountingObjectIds))
                .map { fullAccountingObject ->
                    val inventoryStatus = getInventoryStatus(
                        inventoryRecords = inventoryRecords,
                        fullAccountingObject = fullAccountingObject
                    )
                    fullAccountingObject.toSyncEntity(
                        inventoryStatus
                    )
                }

        return fullInventory.inventoryDb.toInventorySyncEntity(
            organizationSyncEntity = requireNotNull(fullInventory.organizationDb).toSyncEntity(),
            mol = fullInventory.employeeDb?.toSyncEntity(),
            locationSyncEntities = locations,
            accountingObjects = accountingObjects
        )
    }

    override suspend fun updateInventory(inventoryUpdateSyncEntity: InventoryUpdateSyncEntity) {
        inventoryDao.update(inventoryUpdateSyncEntity.toInventoryDb())
        updateRecords(
            inventoryId = inventoryUpdateSyncEntity.id,
            accountingObjectIds = inventoryUpdateSyncEntity.accountingObjectsIds
        )
    }

    private suspend fun updateRecords(
        accountingObjectIds: List<AccountingObjectInfoSyncEntity>,
        inventoryId: String
    ) {
        val existRecords = inventoryRecordDao.getAll(
            sqlInventoryRecordQuery(
                inventoryId = inventoryId,
                accountingObjectIds = accountingObjectIds.map { it.id }
            )
        )
        val newRecords = accountingObjectIds.map { info ->
            val existRecord = existRecords.find { it.accountingObjectId == info.id }
            InventoryRecordDb(
                id = existRecord?.id ?: UUID.randomUUID().toString(),
                accountingObjectId = info.id,
                inventoryStatus = info.status,
                inventoryId = inventoryId,
                updateDate = System.currentTimeMillis()
            )
        }
        inventoryRecordDao.insertAll(newRecords)
    }

    private fun getInventoryStatus(
        inventoryRecords: List<InventoryRecordDb>,
        fullAccountingObject: FullAccountingObject
    ): String? {
        return inventoryRecords.find {
            fullAccountingObject.accountingObjectDb.id == it.accountingObjectId
        }?.inventoryStatus
    }
}