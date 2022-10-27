package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.DatabaseItemDb

@Entity(tableName = "reception_item_category")
class ReceptionItemCategoryDb(
    id: String,
    val name: String
) : DatabaseItemDb(id)