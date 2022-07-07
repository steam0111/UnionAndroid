package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "documents")
class DocumentDb(
    id: String = "",
    val organizationId: String?,
    val molId: String?,
    val creationDate: Long,
    updateDate: Long?,
    val completionDate: Long? = null,
    val exploitingId: String? = null,
    val documentType: String,
    val objectType: String,
    val accountingObjectsIds: List<String>? = null,
    val reservesIds: List<String>? = null,
    val locationIds: List<String>? = null,
    val documentStatus: String,
    val documentStatusId: String,
) : SyncItemDb(id, updateDate)