package com.itrocket.union.inventory.data

import com.example.union_sync_api.data.InventorySyncApi
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventoryCreate.data.mapper.map
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getOrganizationId
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class InventoryRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val inventorySyncApi: InventorySyncApi,
) : InventoryRepository {

    override suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity): String =
        withContext(coreDispatchers.io) {
            inventorySyncApi.createInventory(inventoryCreateSyncEntity)
        }

    override suspend fun updateInventory(inventoryUpdateSyncEntity: InventoryUpdateSyncEntity) =
        withContext(coreDispatchers.io) {
            inventorySyncApi.updateInventory(inventoryUpdateSyncEntity)
        }

    override suspend fun getInventoryById(id: String): InventoryCreateDomain =
        withContext(coreDispatchers.io) {
            inventorySyncApi.getInventoryById(id).map()
        }

    override suspend fun getInventories(
        textQuery: String?,
        params: List<ParamDomain>?
    ): Flow<List<InventoryCreateDomain>> =
        withContext(coreDispatchers.io) {
            inventorySyncApi.getInventories(
                textQuery = textQuery,
                organizationId = params?.getOrganizationId(),
                molId = params?.getMolId()
            ).map {
                it.map()
            }.flowOn(coreDispatchers.io)
        }

}