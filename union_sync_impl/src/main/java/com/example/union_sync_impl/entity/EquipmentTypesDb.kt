package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "equipment_types")
class EquipmentTypesDb(
    id: String,
    override var catalogItemName: String,
    val name: String?,
    val code: String?,
    updateDate: Long?
) : CatalogItemDb(id, updateDate)