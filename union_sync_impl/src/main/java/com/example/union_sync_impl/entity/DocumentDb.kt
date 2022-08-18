package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "documents")
class DocumentDb(
    id: String = "",
    val molId: String?,
    val creationDate: Long?,
    updateDate: Long?,
    val completionDate: Long? = null,
    val exploitingId: String? = null,
    val documentType: String,
    val documentStatus: String,
    val documentStatusId: String,
    val locationFromId: String? = null,
    val locationToId: String? = null,
    val actionBaseId: String? = null,
    val code: String?,
    val structuralFromId: String?,
    val structuralToId: String?,
    userInserted: String?,
    userUpdated: String?
) : SyncItemDb(
    id = id,
    insertDate = creationDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)