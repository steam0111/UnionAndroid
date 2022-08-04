package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "action_remains_record")
class ActionRemainsRecordDb(
    id: String,
    val actionId: String,
    val remainId: String,
    val count: Long?,
    updateDate: Long,
    userInserted: String?,
    userUpdated: String?,
): SyncItemDb(id, updateDate, userUpdated, userInserted)