package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.StructuralSyncEntity
import com.example.union_sync_impl.entity.structural.StructuralDb
import com.example.union_sync_impl.entity.structural.StructuralPathDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import org.openapitools.client.models.StructuralUnitDtoV2
import org.openapitools.client.models.StructuralUnitPathDtoV2

fun StructuralUnitPathDtoV2.toStructuralPathDb(): StructuralPathDb {
    val ancestor = if (descendantId == ancestorId) null else ancestorId
    return StructuralPathDb(
        descendantStructuralId = descendantId,
        ancestorStructuralId = ancestor
    )
}

fun StructuralUnitDtoV2.toStructuralDb(): StructuralDb {
    return StructuralDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        parentId = parentId,
        name = name.orEmpty(),
        balanceUnit = balanceUnit,
        balanceUnitCode = balanceUnitCode,
        branch = branch,
        fullCode = fullCode,
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        insertDate = getMillisDateFromServerFormat(dateInsert),
        userInserted = userInserted,
        userUpdated = userUpdated
    )
}

fun StructuralDb.toStructuralSyncEntity(): StructuralSyncEntity {
    return StructuralSyncEntity(
        id = id,
        name = name,
        balanceUnit = balanceUnit ?: false
    )
}