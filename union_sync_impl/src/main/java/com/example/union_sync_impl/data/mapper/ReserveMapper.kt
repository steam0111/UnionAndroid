package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_impl.entity.FullReserve
import com.example.union_sync_impl.entity.ReserveDb
import org.openapitools.client.models.CustomRemainsDto

fun CustomRemainsDto.toReserveDb(): ReserveDb {
    return ReserveDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        locationId = locationToId,
        molId = molId,
        orderId = orderId,
        nomenclatureId = nomenclatureId,
        nomenclatureGroupId = nomenclatureGroupId,
        businessUnitId = businessUnitId,
        name = name.orEmpty(),
        count = count?.toInt() ?: 0,
        receptionItemCategoryId = receptionItemCategoryId,
        structuralSubdivisionId = structuralSubdivisionId,
        receptionDocumentNumber = receptionDocumentNumber,
        unitPrice = unitPrice
    )
}

fun ReserveDb.toSyncEntity(locationSyncEntity: LocationSyncEntity): ReserveSyncEntity {
    return ReserveSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        molId = molId,
        orderId = orderId,
        nomenclatureId = nomenclatureId,
        nomenclatureGroupId = nomenclatureGroupId,
        businessUnitId = businessUnitId.orEmpty(),
        name = name.orEmpty(),
        count = count ?: 0,
        receptionItemCategoryId = receptionItemCategoryId,
        structuralSubdivisionId = structuralSubdivisionId,
        receptionDocumentNumber = receptionDocumentNumber,
        unitPrice = unitPrice,
        locationSyncEntity = locationSyncEntity,
    )
}

fun FullReserve.toSyncEntity(locationSyncEntity: LocationSyncEntity?): ReserveSyncEntity {
    return ReserveSyncEntity(
        id = reserveDb.id,
        catalogItemName = reserveDb.catalogItemName,
        molId = reserveDb.molId,
        orderId = reserveDb.orderId,
        nomenclatureId = reserveDb.nomenclatureId,
        nomenclatureGroupId = reserveDb.nomenclatureGroupId,
        businessUnitId = reserveDb.businessUnitId.orEmpty(),
        name = reserveDb.name.orEmpty(),
        count = reserveDb.count ?: 0,
        receptionItemCategoryId = reserveDb.receptionItemCategoryId,
        structuralSubdivisionId = reserveDb.structuralSubdivisionId,
        receptionDocumentNumber = reserveDb.receptionDocumentNumber,
        unitPrice = reserveDb.unitPrice,
        locationSyncEntity = locationSyncEntity,
    )
}