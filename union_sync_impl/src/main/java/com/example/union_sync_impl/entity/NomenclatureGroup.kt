package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItem

@Entity(tableName = "nomenclature_group")
class NomenclatureGroup(override var catalogItemName: String) : CatalogItem()