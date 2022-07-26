package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "action_base")
class ActionBaseDb(
    id: String,
    updateDate: Long,
    val name: String
) : SyncItemDb(id, updateDate)
