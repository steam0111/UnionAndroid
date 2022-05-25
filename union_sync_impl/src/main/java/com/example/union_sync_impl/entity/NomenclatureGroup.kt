package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity(tableName = "nomenclature_group")
data class NomenclatureGroup(val name: String) : DatabaseItem()