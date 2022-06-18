package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "producer")
class ProducerDb(
    id: String,
    override var catalogItemName: String,
    val name: String?,
    val code: String?,
) : CatalogItemDb(id)