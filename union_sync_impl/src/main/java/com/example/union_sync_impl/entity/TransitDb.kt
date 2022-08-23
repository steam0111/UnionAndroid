package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "transit")
class TransitDb(
    id: String = "",
    updateDate: Long?,
    val molId: String? = null,
    val receivingId: String? = null,
    val vehicleId: String? = null,
    val locationFromId: String? = null,
    val locationToId: String? = null,
    val creationDate: Long?,
    val completionDate: Long? = null,
    val transitType: String,
    val transitStatus: String,
    val transitStatusId: String,
    val code: String?,
    val structuralFromId: String?,
    val structuralToId: String?,
    userInserted: String?,
    userUpdated: String?
) : SyncItemDb(
    id = id,
    insertDate = creationDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)