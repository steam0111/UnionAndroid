package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.ReserveDetailSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.ReserveUpdateSyncEntity
import com.example.union_sync_api.entity.StructuralSyncEntity
import com.example.union_sync_impl.entity.FullReserve
import com.example.union_sync_impl.entity.ReserveDb
import com.example.union_sync_impl.entity.ReserveUpdate
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.RemainsDtoV2

fun RemainsDtoV2.toReserveDb(): ReserveDb {
    return ReserveDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        locationId = locationToId,
        locationTypeId = extendedLocation?.locationTypeId,
        molId = molId,
        orderId = orderId,
        nomenclatureId = nomenclatureId,
        nomenclatureGroupId = nomenclatureGroupId,
        name = name.orEmpty(),
        count = count,
        receptionItemCategoryId = receptionItemCategoryId,
        receptionDocumentNumber = receptionDocumentNumber,
        unitPrice = unitPrice,
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        insertDate = getMillisDateFromServerFormat(dateInsert),
        userUpdated = userUpdated,
        userInserted = userInserted,
        structuralId = structuralUnitId,
        invoiceNumber = invoiceNumber,
        subName = subName,
        traceable = traceable ?: false
    )
}

fun ReserveDb.toRemainsDtoV2(): RemainsDtoV2 {
    return RemainsDtoV2(
        id = id,
        catalogItemName = catalogItemName,
        locationToId = locationId,
        molId = molId,
        orderId = orderId,
        nomenclatureId = nomenclatureId,
        nomenclatureGroupId = nomenclatureGroupId,
        name = name.orEmpty(),
        count = count,
        dateUpdate = getStringDateFromMillis(System.currentTimeMillis()),
        receptionItemCategoryId = receptionItemCategoryId,
        receptionDocumentNumber = receptionDocumentNumber,
        unitPrice = unitPrice,
        deleted = false,
        userUpdated = userUpdated,
        userInserted = userInserted
    )
}

fun ReserveSyncEntity.toReserveDb(): ReserveDb {
    return ReserveDb(
        id = id,
        catalogItemName = catalogItemName,
        locationId = locationSyncEntity?.id,
        locationTypeId = locationSyncEntity?.locationTypeId,
        molId = molId,
        orderId = orderId,
        nomenclatureId = nomenclatureId,
        nomenclatureGroupId = nomenclatureGroupId,
        structuralId = structuralId,
        name = name,
        count = count,
        receptionItemCategoryId = receptionItemCategoryId,
        receptionDocumentNumber = receptionDocumentNumber,
        unitPrice = unitPrice,
        updateDate = System.currentTimeMillis(),
        insertDate = insertDate,
        userUpdated = userUpdated,
        userInserted = userInserted,
        invoiceNumber = invoiceNumber,
        subName = subName,
        traceable = traceable
    )
}

fun ReserveDb.toSyncEntity(locationSyncEntity: LocationSyncEntity?): ReserveSyncEntity {
    return ReserveSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        molId = molId,
        orderId = orderId,
        nomenclatureId = nomenclatureId,
        nomenclatureGroupId = nomenclatureGroupId,
        structuralId = structuralId,
        name = name.orEmpty(),
        count = count,
        receptionItemCategoryId = receptionItemCategoryId,
        receptionDocumentNumber = receptionDocumentNumber,
        unitPrice = unitPrice,
        locationSyncEntity = locationSyncEntity,
        userUpdated = userUpdated,
        userInserted = userInserted,
        invoiceNumber = invoiceNumber,
        subName = subName,
        traceable = traceable,
        insertDate = insertDate,
        updateDate = updateDate
    )
}

fun FullReserve.toSyncEntity(): ReserveSyncEntity {
    return ReserveSyncEntity(
        id = reserveDb.id,
        catalogItemName = reserveDb.catalogItemName,
        molId = reserveDb.molId,
        orderId = reserveDb.orderId,
        nomenclatureId = reserveDb.nomenclatureId,
        nomenclatureGroupId = reserveDb.nomenclatureGroupId,
        name = reserveDb.name.orEmpty(),
        count = reserveDb.count,
        receptionItemCategoryId = reserveDb.receptionItemCategoryId,
        structuralId = structuralDb?.id,
        receptionDocumentNumber = reserveDb.receptionDocumentNumber,
        unitPrice = reserveDb.unitPrice,
        locationSyncEntity = locationDb?.toLocationSyncEntity(locationTypeDb),
        userUpdated = reserveDb.userUpdated,
        userInserted = reserveDb.userInserted,
        invoiceNumber = reserveDb.invoiceNumber,
        subName = reserveDb.subName,
        traceable = reserveDb.traceable,
        insertDate = reserveDb.insertDate,
        updateDate = reserveDb.updateDate
    )
}

fun FullReserve.toDetailSyncEntity(balanceUnits: List<StructuralSyncEntity>?, structurals: List<StructuralSyncEntity>?): ReserveDetailSyncEntity {
    val locationEntity = locationDb?.toLocationSyncEntity(locationTypeDb)
    return ReserveDetailSyncEntity(
        reserveSyncEntity = reserveDb.toSyncEntity(locationEntity),
        locationSyncEntity = locationEntity,
        locationTypeSyncEntity = locationTypeDb?.toSyncEntity(),
        molSyncEntity = molDb?.toSyncEntity(),
        nomenclatureSyncEntity = nomenclatureDb?.toSyncEntity(),
        nomenclatureGroupSyncEntity = nomenclatureGroupDb?.toSyncEntity(),
        orderSyncEntity = orderDb?.toSyncEntity(),
        receptionItemCategorySyncEntity = receptionItemCategoryDb?.toSyncEntity(),
        structuralSyncEntities = structurals,
        balanceUnitSyncEntities = balanceUnits
    )
}

fun ReserveUpdateSyncEntity.toReserveUpdate() = ReserveUpdate(
    id = id,
    count = count,
    locationId = locationId,
    updateDate = System.currentTimeMillis(),
    userUpdated = userUpdated
)