package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounting_object_category")
class AccountingObjectCategoryDb(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String
)