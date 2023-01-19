package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_nomenclature_record_rfid")
class InventoryNomenclatureRecordRfidDb(
    @PrimaryKey(autoGenerate = false) val inventoryId: String,
    val rfids: List<String>
)