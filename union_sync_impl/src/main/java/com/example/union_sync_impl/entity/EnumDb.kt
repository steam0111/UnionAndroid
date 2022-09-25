package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enums")
class EnumDb(
    val id: String,
    val name: String,
    val enumType: String,
    @PrimaryKey(autoGenerate = false)
    val compoundId: String = "${id}_${enumType}"
)
