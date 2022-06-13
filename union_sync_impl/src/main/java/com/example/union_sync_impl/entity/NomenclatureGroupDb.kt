package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "nomenclature_group")
class NomenclatureGroupDb(
    id: String,
    override var catalogItemName: String,
    val name: String
) : CatalogItemDb(id)