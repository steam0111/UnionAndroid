package com.example.union_sync_api.entity

data class ReserveSyncEntity(
    val id: String,
    var catalogItemName: String,
    val locationSyncEntity: List<LocationSyncEntity>?,
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
    val insertDate: Long?,
    val updateDate: Long?,
    val structuralId: String?,
    val traceable: Boolean,
    val invoiceNumber: String?,
    val subName: String?,
    val barcodeValue: String?
)

fun ReserveSyncEntity.toReserveUpdateSyncEntity(userUpdated: String?) = ReserveUpdateSyncEntity(
    id = id,
    count = count ?: 0,
    locationId = locationSyncEntity?.lastOrNull()?.id,
    userUpdated = userUpdated,
    dateUpdate = System.currentTimeMillis()
)

fun ReserveSyncEntity.toReserveShortSyncEntity(locationId: String? = null, userUpdated: String?) =
    ReserveShortSyncEntity(
        locationId = locationId ?: locationSyncEntity?.lastOrNull()?.id,
        nomenclatureId = nomenclatureId,
        orderId = orderId,
        name = name,
        userUpdated = userUpdated,
        updateDate = System.currentTimeMillis()
    )

fun ReserveSyncEntity.toReserveCountSyncEntity() = ReserveCountSyncEntity(
    id = id,
    count = count,
    userUpdated = userUpdated
)