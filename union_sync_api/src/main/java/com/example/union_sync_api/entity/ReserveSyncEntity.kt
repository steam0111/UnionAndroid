package com.example.union_sync_api.entity

import java.math.BigDecimal

data class ReserveSyncEntity(
    val id: String,
    var catalogItemName: String,
    val locationSyncEntity: List<LocationSyncEntity>?,
    val molId: String?,
    val orderId: String?,
    val nomenclatureId: String?,
    val nomenclatureGroupId: String?,
    val name: String,
    val count: BigDecimal?,
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
    val barcodeValue: String?,
    val bookkeepingInvoice: BigDecimal?,
    val consignment: String?,
    val labelTypeId: String?,
    val labelType: LabelTypeSyncEntity? = null,
    val nomenclatureSyncEntity: NomenclatureSyncEntity? = null
)

fun ReserveSyncEntity.toReserveUpdateSyncEntity(userUpdated: String?) = ReserveUpdateSyncEntity(
    id = id,
    count = count ?: BigDecimal.ZERO,
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