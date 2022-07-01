package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reception_item_category")
class ReceptionItemCategoryDb(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String
)