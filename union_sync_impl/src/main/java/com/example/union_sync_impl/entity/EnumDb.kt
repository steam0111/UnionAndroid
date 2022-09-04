package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "enums")
class EnumDb(
    id: String,
    val name: String,
    val enumType: String
) : SyncItemDb(id)