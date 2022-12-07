package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "label_type")
class LabelTypeDb(
    id: String,
    cancel: Boolean? = false,
    userInserted: String?,
    userUpdated: String?,
    updateDate: Long?,
    insertDate: Long?,
    val name: String?,
    val description: String?,
    val code: String?,
    override var catalogItemName: String,
) : CatalogItemDb(
    id = id,
    cancel = cancel,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)