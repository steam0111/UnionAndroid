package com.example.union_sync_impl.entity.location

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(
    tableName = "locationTypes"
)
class LocationTypeDb(
    id: String,
    override var catalogItemName: String,
    val parentId: String? = null,
    val name: String,
    updateDate: Long?
) : CatalogItemDb(id, updateDate)