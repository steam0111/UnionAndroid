package com.example.union_sync_impl.data

import com.example.union_sync_api.data.EnumsSyncApi
import com.example.union_sync_api.entity.EnumSyncEntity
import com.example.union_sync_api.entity.EnumType
import com.example.union_sync_impl.dao.EnumsDao
import com.example.union_sync_impl.dao.sqlEnumsQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity

class EnumsSyncApiImpl(
    private val dao: EnumsDao
) : EnumsSyncApi {
    override suspend fun getAllByType(
        enumType: EnumType,
        textQuery: String?,
        id: String?
    ): List<EnumSyncEntity> {
        return dao.getAll(sqlEnumsQuery(enumType = enumType.name, name = textQuery, id = id))
            .map { it.toSyncEntity() }
    }
}