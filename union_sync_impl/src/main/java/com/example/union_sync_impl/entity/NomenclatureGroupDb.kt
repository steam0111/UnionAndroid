package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "nomenclature_group")
class NomenclatureGroupDb(
    id: String,
    override var catalogItemName: String,
    val name: String,
    val code: String?,
    cancel: Boolean? = false,
    insertDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : CatalogItemDb(
    id = id,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted,
    cancel = cancel
)