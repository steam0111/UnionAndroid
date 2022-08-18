package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_base")
class InventoryBaseDb(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String
)