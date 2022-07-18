package com.example.union_sync_impl.data

import com.example.union_sync_api.data.InventorySyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.entity.*
import com.example.union_sync_impl.dao.*
import com.example.union_sync_impl.data.mapper.toInventoryDb
import com.example.union_sync_impl.data.mapper.toInventorySyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.FullAccountingObject
import com.example.union_sync_impl.entity.FullInventory
import com.example.union_sync_impl.entity.InventoryDb
import com.example.union_sync_impl.entity.InventoryRecordDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
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
            if (it.isNotBlank()) {
                locationSyncApi.getLocationById(it)
            } else {
                null
            }
        }?.filterNotNull().orEmpty()
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
        Timber.tag(INVENTORY_TAG).d("getInventoryById id : $id")

        val fullInventory = inventoryDao.getInventoryById(id)

        Timber.tag(INVENTORY_TAG)
            .d("getInventoryById status ${fullInventory.inventoryDb.inventoryStatus}")

        val locations = fullInventory.inventoryDb.locationIds?.map { locationId ->
            if (locationId.isNotBlank()) {
                locationSyncApi.getLocationById(locationId)
            } else {
                null
            }
        }?.filterNotNull()

        return fullInventory.inventoryDb.toInventorySyncEntity(
            organizationSyncEntity = fullInventory.organizationDb?.toSyncEntity(),
            mol = fullInventory.employeeDb?.toSyncEntity(),
            locationSyncEntities = locations,
            accountingObjects = getAccountingObjects(fullInventory.inventoryDb)
        ).apply {
            Timber.tag(INVENTORY_TAG)
                .d("InventorySyncEntity accountingObjectsIds : ${accountingObjects.map { it.id }}")
        }
    }

    private suspend fun getAccountingObjects(
        inventoryDb: InventoryDb
    ): List<AccountingObjectSyncEntity> {
        /*
            Если status = CREATED, значит при создание ИВ мы не сохраняли ОУ в records,
            т.о мы должны их подтнянуть использую фильтрация по полям в самой ИВ
        * */
        return if (inventoryDb.inventoryStatus == INVENTORY_STATUS_CREATED) {
            Timber.tag(INVENTORY_TAG)
                .d(
                    "getInventoryById getAccountingObjects " +
                            "organizationId: ${inventoryDb.organizationId} " +
                            "employeeId: ${inventoryDb.employeeId} " +
                            "locationIds: ${inventoryDb.locationIds}"
                )

            accountingObjectDao.getAll(
                sqlAccountingObjectQuery(
                    organizationId = inventoryDb.organizationId,
                    molId = inventoryDb.employeeId,
                    locationIds = if (inventoryDb.locationIds.isNullOrEmpty()) {
                        null
                    } else {
                        inventoryDb.locationIds
                    }
                )
            ).map {
                val location = locationSyncApi.getLocationById(it.accountingObjectDb.locationId)
                it.toSyncEntity(locationSyncEntity = location)
            }
        } else {
            val inventoryRecords =
                inventoryRecordDao.getAll(sqlInventoryRecordQuery(inventoryDb.id))

            val accountingObjectIds = inventoryRecords.map {
                it.accountingObjectId
            }

            accountingObjectDao.getAll(sqlAccountingObjectQuery(accountingObjectsIds = accountingObjectIds))
                .map { fullAccountingObject ->
                    val inventoryStatus = getInventoryStatus(
                        inventoryRecords = inventoryRecords,
                        fullAccountingObject = fullAccountingObject
                    )
                    val location =
                        locationSyncApi.getLocationById(fullAccountingObject.accountingObjectDb.locationId)
                    fullAccountingObject.toSyncEntity(
                        inventoryStatus = inventoryStatus,
                        locationSyncEntity = location
                    )
                }
        }
    }

    override suspend fun updateInventory(inventoryUpdateSyncEntity: InventoryUpdateSyncEntity) {
        inventoryDao.update(inventoryUpdateSyncEntity.toInventoryDb())

        if (inventoryUpdateSyncEntity.inventoryStatus != INVENTORY_STATUS_CREATED) {
            updateRecords(
                inventoryId = inventoryUpdateSyncEntity.id,
                accountingObjectIds = inventoryUpdateSyncEntity.accountingObjectsIds
            )
        }
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

        Timber.tag(INVENTORY_TAG)
            .d("updateRecords newRecords size : ${newRecords.size}")

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

    companion object {
        const val INVENTORY_TAG = "INVENTORY_TAG"
        private const val INVENTORY_STATUS_CREATED = "CREATED"
    }
}