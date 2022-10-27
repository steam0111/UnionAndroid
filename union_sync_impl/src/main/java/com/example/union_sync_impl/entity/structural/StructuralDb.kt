package com.example.union_sync_impl.entity.structural

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "structural")
class StructuralDb(
    id: String,
    cancel: Boolean? = false,
    override var catalogItemName: String,
    var parentId: String? = null,
    val name: String,
    val balanceUnit: Boolean? = null,
    val balanceUnitCode: String? = null,
    val branch: Boolean? = null,
    val fullCode: String? = null,
    insertDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : CatalogItemDb(
    id = id,
    cancel = cancel,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)