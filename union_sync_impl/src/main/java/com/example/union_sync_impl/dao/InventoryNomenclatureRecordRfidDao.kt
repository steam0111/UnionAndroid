package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.InventoryNomenclatureRecordRfidDb

@Dao
interface InventoryNomenclatureRecordRfidDao {

    @Query("SELECT * FROM inventory_nomenclature_record_rfid WHERE inventoryId = :inventoryId")
    suspend fun getById(inventoryId: String): InventoryNomenclatureRecordRfidDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: InventoryNomenclatureRecordRfidDb)
}