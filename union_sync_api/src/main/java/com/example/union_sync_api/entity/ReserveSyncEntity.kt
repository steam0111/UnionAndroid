package com.example.union_sync_api.entity

data class ReserveSyncEntity(
    val id: String,
    var catalogItemName: String,
    val locationSyncEntity: LocationSyncEntity?,
    val molId: String?,
    val orderId: String?,
    val nomenclatureId: String?,
    val nomenclatureGroupId: String?,
    val name: String,
    val count: Long?,
    val receptionItemCategoryId: String?,
    val receptionDocumentNumber: String?,
    val unitPrice: String?,
    val userInserted: String?,
    val userUpdated: String?,
    val structuralId: String?,
    val traceable: Boolean,
    val invoiceNumber: String?,
    val subName: String?
)

fun ReserveSyncEntity.toReserveUpdateSyncEntity(userUpdated: String?) = ReserveUpdateSyncEntity(
    id = id,
    count = count ?: 0,
    locationId = locationSyncEntity?.id,
    userUpdated = userUpdated
)

fun ReserveSyncEntity.toReserveShortSyncEntity(locationId: String? = null, userUpdated: String?) = ReserveShortSyncEntity(
    locationId = locationId ?: locationSyncEntity?.id,
    nomenclatureId = nomenclatureId,
    orderId = orderId,
    name = name,
    userUpdated = userUpdated
)

fun ReserveSyncEntity.toDocumentReserveCountSyncEntity() = DocumentReserveCountSyncEntity(
    id = id,
    count = count,
    userUpdated = userUpdated
)