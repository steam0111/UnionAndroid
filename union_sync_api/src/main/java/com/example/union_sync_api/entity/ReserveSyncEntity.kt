package com.example.union_sync_api.entity

data class ReserveSyncEntity(
    val id: String,
    var catalogItemName: String,
    val locationSyncEntity: LocationSyncEntity?,
    val molId: String?,
    val orderId: String?,
    val nomenclatureId: String?,
    val nomenclatureGroupId: String?,
    val businessUnitId: String,
    val name: String,
    val count: Int?,
    val receptionItemCategoryId: String?,
    val structuralSubdivisionId: String?,
    val receptionDocumentNumber: String?,
    val unitPrice: String?,
)