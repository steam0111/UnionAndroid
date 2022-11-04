package com.example.union_sync_impl.data

import com.example.union_sync_api.data.EnumsSyncApi
import com.example.union_sync_api.data.InventoryCheckerSyncApi
import com.example.union_sync_api.data.InventorySyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.entity.AccountingObjectInfoSyncEntity
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.EnumType
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.StructuralSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.dao.InventoryRecordDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.dao.sqlInventoryQuery
import com.example.union_sync_impl.dao.sqlInventoryRecordQuery
import com.example.union_sync_impl.data.mapper.toInventoryDb
import com.example.union_sync_impl.data.mapper.toInventorySyncEntity
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toStructuralSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.FullInventory
import com.example.union_sync_impl.entity.InventoryDb
import com.example.union_sync_impl.entity.InventoryRecordDb
import com.itrocket.core.base.CoreDispatchers
import java.util.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class InventorySyncApiImpl(
    private val inventoryDao: InventoryDao,
    private val locationDao: LocationDao,
    private val structuralSyncApi: StructuralSyncApi,
    private val accountingObjectDao: AccountingObjectDao,
    private val inventoryRecordDao: InventoryRecordDao,
    private val locationSyncApi: LocationSyncApi,
    private val checkerSyncApi: InventoryCheckerSyncApi,
    private val enumsApi: EnumsSyncApi,
    private val coreDispatchers: CoreDispatchers
) : InventorySyncApi {
    override suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity): String {
        val inventoryId = UUID.randomUUID().toString()
        inventoryDao.insert(inventoryCreateSyncEntity.toInventoryDb(inventoryId))
        updateRecords(
            inventoryId = inventoryId,
            accountingObjectIds = inventoryCreateSyncEntity.accountingObjectsIds,
            userUpdated = inventoryCreateSyncEntity.userUpdated
        )
        return inventoryId
    }

    override suspend fun getInventories(
        textQuery: String?,
        structuralId: String?,
        molId: String?,
        inventoryBaseId: String?,
        offset: Long,
        limit: Long?
    ): List<InventorySyncEntity> {
        return inventoryDao.getAll(
            sqlInventoryQuery(
                textQuery = textQuery,
                structuralId = structuralId,
                molId = molId,
                inventoryBaseId = inventoryBaseId,
                offset = offset,
                limit = limit
            )
        ).map {
            val structurals = listOfNotNull(it.structuralDb?.toStructuralSyncEntity())
            val balanceUnit = structurals.lastOrNull { it.balanceUnit }
            val balanceUnitFullPath =
                structuralSyncApi.getStructuralFullPath(balanceUnit?.id, mutableListOf())
            val inventoryBase = enumsApi.getByCompoundId(
                enumType = EnumType.INVENTORY_BASE,
                id = it.inventoryDb.inventoryBaseId
            )

            it.inventoryDb.toInventorySyncEntity(
                structuralSyncEntities = structurals,
                mol = it.employeeDb?.toSyncEntity(),
                locationSyncEntities = it.getLocations(),
                accountingObjects = listOf(),
                inventoryBaseSyncEntity = inventoryBase,
                balanceUnit = balanceUnitFullPath.orEmpty(),
                checkers = checkerSyncApi.getCheckers(it.inventoryDb.id)
            )
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
        textQuery: String?, structuralId: String?, molId: String?, inventoryBaseId: String?
    ): Long {
        return inventoryDao.getCount(
            sqlInventoryQuery(
                textQuery = textQuery,
                structuralId = structuralId,
                molId = molId,
                isFilterCount = true,
                inventoryBaseId = inventoryBaseId
            )
        )
    }

    override suspend fun getInventoryById(
        id: String,
        isAccountingObjectLoad: Boolean
    ): InventorySyncEntity = coroutineScope {
        val fullInventory = inventoryDao.getInventoryById(id)

        val structuralsAndBalanceUnitFullPath: Deferred<Pair<List<StructuralSyncEntity>, List<StructuralSyncEntity>>> =
            async(coreDispatchers.io) {
                val structurals = structuralSyncApi.getStructuralFullPath(
                    fullInventory.structuralDb?.id, mutableListOf()
                ).orEmpty()

                val balanceUnit = structurals.lastOrNull { it.balanceUnit }
                val balanceUnitFullPath =
                    structuralSyncApi.getStructuralFullPath(balanceUnit?.id, mutableListOf())
                        .orEmpty()

                return@async structurals to balanceUnitFullPath
            }

        val inventoryBaseDeferred = async(coreDispatchers.io) {
            enumsApi.getByCompoundId(
                enumType = EnumType.INVENTORY_BASE, id = fullInventory.inventoryDb.inventoryBaseId
            )
        }

        val accountingObjectsDeferred = async(coreDispatchers.io) {
            if (isAccountingObjectLoad) {
                getAccountingObjects(fullInventory.inventoryDb)
            } else {
                listOf()
            }
        }

        val locationsDeferred = async(coreDispatchers.io) {
            if (fullInventory.inventoryDb.locationIds != null) {
                locationSyncApi.getLocationsByIds(fullInventory.inventoryDb.locationIds)
            } else {
                listOf()
            }
        }

        val checkersDeferred = async(coreDispatchers.io) {
            checkerSyncApi.getCheckers(fullInventory.inventoryDb.id)
        }

        val (structurals, balanceUnitFullPath) = structuralsAndBalanceUnitFullPath.await()

        fullInventory.inventoryDb.toInventorySyncEntity(
            structuralSyncEntities = structurals,
            mol = fullInventory.employeeDb?.toSyncEntity(),
            locationSyncEntities = locationsDeferred.await(),
            accountingObjects = accountingObjectsDeferred.await(),
            inventoryBaseSyncEntity = inventoryBaseDeferred.await(),
            balanceUnit = balanceUnitFullPath,
            checkers = checkersDeferred.await()
        )
    }

    private suspend fun getAccountingObjects(
        inventoryDb: InventoryDb
    ): List<AccountingObjectSyncEntity> {
        val inventoryRecords = inventoryRecordDao.getAll(sqlInventoryRecordQuery(inventoryDb.id))

        val accountingObjectsIdStatusMap = mutableMapOf<String, String>()

        inventoryRecords.forEach {
            accountingObjectsIdStatusMap[it.accountingObjectId] = it.inventoryStatus
        }

        return accountingObjectDao.getAll(sqlAccountingObjectQuery(accountingObjectsIds = accountingObjectsIdStatusMap.keys.toList()))
            .map { fullAccountingObject ->
                fullAccountingObject.toSyncEntity(
                    inventoryStatus = accountingObjectsIdStatusMap[fullAccountingObject.accountingObjectDb.id],
                    locationSyncEntity = listOfNotNull(
                        fullAccountingObject.locationDb?.toLocationSyncEntity(
                            fullAccountingObject.locationTypesDb
                        )
                    )
                )
            }
    }

    override suspend fun updateInventory(inventoryUpdateSyncEntity: InventoryUpdateSyncEntity) {
        inventoryDao.update(inventoryUpdateSyncEntity.toInventoryDb())

        if (inventoryUpdateSyncEntity.inventoryStatus != INVENTORY_STATUS_CREATED) {
            updateRecords(
                inventoryId = inventoryUpdateSyncEntity.id,
                accountingObjectIds = inventoryUpdateSyncEntity.accountingObjectsIds,
                userUpdated = inventoryUpdateSyncEntity.userUpdated
            )
        }
    }

    private suspend fun updateRecords(
        accountingObjectIds: List<AccountingObjectInfoSyncEntity>,
        inventoryId: String,
        userUpdated: String?
    ) {
        val mappedIds = accountingObjectIds.map { it.id }
        val records = inventoryRecordDao.getAll(
            sqlInventoryRecordQuery(inventoryId = inventoryId)
        ).map {
            it.copy(cancel = !mappedIds.contains(it.accountingObjectId))
        }
        val existRecords = records.filter { mappedIds.contains(it.accountingObjectId) }
        val newRecords = accountingObjectIds.map { info ->
            val existRecord = existRecords.find { it.accountingObjectId == info.id }
            val updateDate = if (existRecord?.inventoryStatus == info.status) {
                existRecord.updateDate
            } else {
                System.currentTimeMillis()
            }
            InventoryRecordDb(
                id = existRecord?.id ?: UUID.randomUUID().toString(),
                accountingObjectId = info.id,
                inventoryStatus = info.status,
                inventoryId = inventoryId,
                updateDate = updateDate,
                insertDate = existRecord?.insertDate,
                userUpdated = userUpdated,
                userInserted = userUpdated,
                cancel = false
            )
        }

        Timber.tag(INVENTORY_TAG).d("updateRecords newRecords size : ${newRecords.size}")

        inventoryRecordDao.insertAll(newRecords)

        val removedAccountingObjects = records.filter { it.cancel == true }
            .map { it.copy(updateDate = System.currentTimeMillis()) }
        inventoryRecordDao.insertAll(removedAccountingObjects)
    }

    companion object {
        const val INVENTORY_TAG = "INVENTORY_TAG"
        private const val INVENTORY_STATUS_CREATED = "CREATED"
    }
}