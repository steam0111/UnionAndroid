package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
class DocumentDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val organizationId: String,
    val molId: String,
    val exploitingId: String?,
    val documentType: String,
    val accountingObjectsIds: List<String>,
    val date: Long
)