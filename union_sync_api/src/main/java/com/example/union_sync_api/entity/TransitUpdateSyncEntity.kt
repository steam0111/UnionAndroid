package com.example.union_sync_api.entity

data class TransitUpdateSyncEntity(
    val id: String,
    val molId: String?,
    val accountingObjectsIds: List<String>? = null,
    val locationFromId: String? = null,
    val locationToId: String? = null,
    val creationDate: Long,
    val completionDate: Long?,
    val documentStatus: String,
    val documentStatusId: String,
    val reservesIds: List<ReserveCountSyncEntity>? = null,
    val code: String?,
    val responsibleId: String? = null,
    val receivingId: String? = null,
    val vehicleId: String? = null,
    val transitType: String,
    val transitStatus: String,
    val transitStatusId: String,
    val structuralUnitFromId: String?,
    val structuralUnitToId: String?,
    val userInserted: String?,
    val userUpdated: String?
)