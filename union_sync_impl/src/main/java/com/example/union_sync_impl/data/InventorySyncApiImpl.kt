package com.example.union_sync_impl.data

import com.example.union_sync_api.data.InventorySyncApi
import com.example.union_sync_api.entity.AccountingObjectInfoSyncEntity
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventoryRecordSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.dao.InventoryRecordDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.dao.sqlActionRecordQuery
import com.example.union_sync_impl.dao.sqlInventoryQuery
import com.example.union_sync_impl.dao.sqlInventoryRecordQuery
import com.example.union_sync_impl.data.mapper.toInventoryDb
import com.example.union_sync_impl.data.mapper.toInventorySyncEntity
import com.example.union_sync_impl.data.mapper.toLocationShortSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.ActionRecordDb
import com.example.union_sync_impl.entity.FullAccountingObject
import com.example.union_sync_impl.entity.InventoryDb
import com.example.union_sync_impl.entity.InventoryRecordDb
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InventorySyncApiImpl(
    private val inventoryDao: InventoryDao,
    private val locationDao: LocationDao,
    private val accountingObjectDao: AccountingObjectDao,
    private val inventoryRecordDao: InventoryRecordDao
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
                    locationSyncEntities = listOf(),
                    accountingObjects = listOf()
                )
            }
        }
    }

    override suspend fun getInventoryById(id: String): InventorySyncEntity {
        val fullInventory = inventoryDao.getInventoryById(id)

        val locationIds = fullInventory.inventoryDb.locationIds

        val locations = if (locationIds != null) {
            locationDao.getLocationsByIds(locationIds).map {
                it.toLocationShortSyncEntity()
            }
        } else {
            null
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
                        getLocationSyncEntity(fullAccountingObject.locationDb),
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

    //TODO переделать на join
    private suspend fun getLocationSyncEntity(locationDb: LocationDb?): LocationSyncEntity? {
        if (locationDb == null) {
            return null
        }
        val locationTypeId = locationDb.locationTypeId ?: return null
        val locationTypeDb: LocationTypeDb =
            locationDao.getLocationTypeById(locationTypeId) ?: return null

        return locationDb.toLocationSyncEntity(locationTypeDb)
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