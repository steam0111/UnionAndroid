package com.example.union_sync_impl.data

import com.example.union_sync_api.data.InventorySyncApi
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.data.mapper.toInventoryDb
import com.example.union_sync_impl.data.mapper.toInventorySyncEntity
import com.example.union_sync_impl.data.mapper.toLocationShortSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InventorySyncApiImpl(
    private val inventoryDao: InventoryDao,
    private val locationDao: LocationDao,
    private val accountingObjectDao: AccountingObjectDao
) : InventorySyncApi {
    override suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity): Long {
        return inventoryDao.insert(inventoryCreateSyncEntity.toInventoryDb())
    }

    override suspend fun getInventories(): Flow<List<InventorySyncEntity>> {
        return inventoryDao.getAll().map {
            it.map {
                it.inventoryDb.toInventorySyncEntity(
                    organizationSyncEntity = requireNotNull(it.organizationDb).toSyncEntity(),
                    mol = it.employeeDb?.toSyncEntity(),
                    locationSyncEntities = listOf(),
                    accountingObjects = listOf()
                )
            }
        }
    }

    override suspend fun getInventoryById(id: Long): InventorySyncEntity {
        val fullInventory = inventoryDao.getInventoryById(id)

        val locationIds = fullInventory.inventoryDb.locationIds

        val locations = if (locationIds != null) {
            locationDao.getLocationsByIds(locationIds).map {
                it.toLocationShortSyncEntity()
            }
        } else {
            null
        }

        val accountingObjects =
            accountingObjectDao.getAll(sqlAccountingObjectQuery(accountingObjectsIds = fullInventory.inventoryDb.accountingObjectsIds))
                .map {
                    it.toSyncEntity(it, getLocationSyncEntity(it.locationDb))
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
    }

    //TODO переделать на join
    private suspend fun getLocationSyncEntity(locationDb: LocationDb?): LocationSyncEntity? {
        if (locationDb == null) {
            return null
        }
        val locationTypeId = locationDb.locationTypeId ?: return null
        val locationTypeDb: LocationTypeDb = locationDao.getLocationTypeById(locationTypeId)

        return locationDb.toLocationSyncEntity(locationTypeDb)
    }
}