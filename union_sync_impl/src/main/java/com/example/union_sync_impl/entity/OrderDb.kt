package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "orders")
class OrderDb(
    id: String,
    override var catalogItemName: String,
    val number: String?,
    val summary: String?,
    val date: String?,
    updateDate: Long?
) : CatalogItemDb(id, updateDate)