package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.DatabaseItemDb
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "action_record")
class ActionRecordDb(
    id: String,
    val actionId: String,
    val accountingObjectId: String,
    updateDate: Long
): SyncItemDb(id, updateDate)