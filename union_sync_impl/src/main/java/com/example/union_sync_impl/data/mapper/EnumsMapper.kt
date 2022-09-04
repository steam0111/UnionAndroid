package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.EnumSyncEntity
import com.example.union_sync_api.entity.EnumType
import com.example.union_sync_impl.entity.EnumDb
import org.openapitools.client.models.EnumDtoV2

fun EnumDtoV2.toEnumDb(type: EnumType): EnumDb {
    return EnumDb(
        id = id,
        name = name.orEmpty(),
        enumType = type.name
    )
}

fun EnumDb.toSyncEntity(): EnumSyncEntity {
    return EnumSyncEntity(
        id = id,
        name = name,
        enumType = EnumType.valueOf(enumType)
    )
}

fun EnumSyncEntity.toEnumDb(): EnumDb {
    return EnumDb(
        id = id,
        name = name,
        enumType = enumType.name
    )
}