package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.ReserveDetailSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_impl.entity.FullReserve
import com.example.union_sync_impl.entity.ReserveDb
import org.openapitools.client.models.RemainsDtoV2

fun RemainsDtoV2.toReserveDb(): ReserveDb {
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
        count = count?.toLong(),
        receptionItemCategoryId = receptionItemCategoryId,
        structuralSubdivisionId = structuralSubdivisionId,
        receptionDocumentNumber = receptionDocumentNumber,
        unitPrice = unitPrice,
        updateDate = System.currentTimeMillis()
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
        businessUnitId = businessUnitId,
        name = name.orEmpty(),
        count = count,
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
        businessUnitId = reserveDb.businessUnitId,
        name = reserveDb.name.orEmpty(),
        count = reserveDb.count,
        receptionItemCategoryId = reserveDb.receptionItemCategoryId,
        structuralSubdivisionId = reserveDb.structuralSubdivisionId,
        receptionDocumentNumber = reserveDb.receptionDocumentNumber,
        unitPrice = reserveDb.unitPrice,
        locationSyncEntity = locationSyncEntity,
    )
}

fun FullReserve.toDetailSyncEntity(locationSyncEntity: LocationSyncEntity?): ReserveDetailSyncEntity {
    return ReserveDetailSyncEntity(
        reserveSyncEntity = reserveDb.toSyncEntity(locationSyncEntity),
        businessUnitSyncEntity = businessUnitDb?.toSyncEntity(),
        locationSyncEntity = locationSyncEntity,
        molSyncEntity = molDb?.toSyncEntity(),
        nomenclatureSyncEntity = nomenclatureDb?.toSyncEntity(),
        nomenclatureGroupSyncEntity = nomenclatureGroupDb?.toSyncEntity(),
        orderSyncEntity = orderDb?.toSyncEntity(),
        receptionItemCategorySyncEntity = receptionItemCategoryDb?.toSyncEntity(),
        structuralSubdivisionSyncEntity = structuralSubdivisionDb?.toSyncEntity(),
    )
}