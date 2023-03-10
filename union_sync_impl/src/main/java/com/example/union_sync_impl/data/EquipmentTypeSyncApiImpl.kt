package com.example.union_sync_impl.data

import com.example.union_sync_api.data.EquipmentTypeSyncApi
import com.example.union_sync_api.entity.EquipmentTypeSyncEntity
import com.example.union_sync_impl.dao.EquipmentTypeDao
import com.example.union_sync_impl.dao.sqlEquipmentTypeQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EquipmentTypeSyncApiImpl(
    private val equipmentTypeDao: EquipmentTypeDao,
) : EquipmentTypeSyncApi {
    override suspend fun getEquipmentTypesFlow(textQuery: String?): Flow<List<EquipmentTypeSyncEntity>> {
        return flow {
            emit(
                equipmentTypeDao.getAll(sqlEquipmentTypeQuery(textQuery)).map { it.toSyncEntity() })
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getEquipmentTypes(
        textQuery: String?,
        offset: Long?,
        limit: Long?
    ): List<EquipmentTypeSyncEntity> {
        return equipmentTypeDao.getAll(
            sqlEquipmentTypeQuery(
                textQuery,
                limit = limit,
                offset = offset
            )
        ).map { it.toSyncEntity() }
    }

    override suspend fun getEquipmentTypeDetail(id: String): EquipmentTypeSyncEntity {
        return equipmentTypeDao.getById(id).toSyncEntity()
    }
}