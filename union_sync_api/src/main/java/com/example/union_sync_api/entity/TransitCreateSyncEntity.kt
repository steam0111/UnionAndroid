package com.example.union_sync_api.entity

data class TransitCreateSyncEntity(
    val responsibleId: String? = null,
    val receivingId: String? = null,
    val vehicleId: String? = null,
    val locationFromId: String? = null,
    val locationToId: String? = null,
    val transitType: String,
    val transitStatus: String,
    val transitStatusId: String,
    val code: String?,
    val accountingObjectsIds: List<String>? = null,
    val reservesIds: List<ReserveCountSyncEntity>? = null,
    val structuralUnitFromId: String?,
    val structuralUnitToId: String?,
    val userInserted: String?,
    val userUpdated: String?
)