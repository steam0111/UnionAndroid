package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
class DocumentDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val organizationId: String,
    val molId: String,
    val creationDate: Long,
    val completionDate: Long? = null,
    val exploitingId: String? = null,
    val documentType: String,
    val objectType: String,
    val accountingObjectsIds: List<String>? = null,
    val reservesIds: List<String>? = null,
    val locationIds: List<String>? = null,
    val documentStatus: String,
    val documentStatusId: String,
)