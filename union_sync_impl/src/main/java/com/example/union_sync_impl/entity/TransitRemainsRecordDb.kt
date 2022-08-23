package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "transit_remains_record")
class TransitRemainsRecordDb(
    id: String,
    val transitId: String?,
    val remainId: String?,
    val count: Long?,
    updateDate: Long?,
    insertDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : SyncItemDb(
    id = id,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)