package com.example.union_sync_api.entity

data class ReserveSyncEntity(
    val id: String,
    var catalogItemName: String,
    val locationSyncEntity: LocationSyncEntity?,
    val molId: String?,
    val orderId: String?,
    val nomenclatureId: String?,
    val nomenclatureGroupId: String?,
    val businessUnitId: String?,
    val name: String,
    val count: Long?,
    val receptionItemCategoryId: String?,
    val structuralSubdivisionId: String?,
    val receptionDocumentNumber: String?,
    val unitPrice: String?,
)

fun ReserveSyncEntity.toReserveUpdateSyncEntity() = ReserveUpdateSyncEntity(
    id = id,
    count = count ?: 0,
    locationId = locationSyncEntity?.id
)

fun ReserveSyncEntity.toReserveShortSyncEntity(locationId: String? = null) = ReserveShortSyncEntity(
    locationId = locationId ?: locationSyncEntity?.id,
    nomenclatureId = nomenclatureId,
    orderId = orderId,
    name = name
)

fun ReserveSyncEntity.toDocumentReserveCountSyncEntity() = DocumentReserveCountSyncEntity(
    id = id,
    count = count
)