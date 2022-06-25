package com.example.union_sync_impl.data

import com.example.union_sync_api.data.EquipmentTypeSyncApi
import com.example.union_sync_api.entity.EquipmentTypeSyncEntity
import com.example.union_sync_impl.dao.EquipmentTypeDao
import com.example.union_sync_impl.data.mapper.toEquipmentTypeDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.openapitools.client.custom_api.EquipmentTypeApi

class EquipmentTypeSyncApiImpl(
    private val equipmentTypeDao: EquipmentTypeDao,
    private val equipmentTypeApi: EquipmentTypeApi
) : EquipmentTypeSyncApi {
    override suspend fun getEquipmentTypes(): Flow<List<EquipmentTypeSyncEntity>> {
        return flow {
            emit(equipmentTypeDao.getAll().map { it.toSyncEntity() })
            val networkData = equipmentTypeApi.apiCatalogEquipmentTypesGet().list.orEmpty()
            networkData.map { it.toEquipmentTypeDb() }.let { dbEquipmentType ->
                equipmentTypeDao.insertAll(dbEquipmentType)
            }
            emit(equipmentTypeDao.getAll().map { it.toSyncEntity() })
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getEquipmentTypeDetail(id: String): EquipmentTypeSyncEntity {
        return equipmentTypeDao.getById(id).toSyncEntity()
    }
}