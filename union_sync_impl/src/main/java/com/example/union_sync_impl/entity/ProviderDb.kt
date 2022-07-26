package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "providers")
class ProviderDb(
    id: String,
    override var catalogItemName: String,
    val name: String?,
    updateDate: Long?
) : CatalogItemDb(id, updateDate)