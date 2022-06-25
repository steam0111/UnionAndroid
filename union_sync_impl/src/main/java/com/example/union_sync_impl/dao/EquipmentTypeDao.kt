package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.EquipmentTypesDb

@Dao
interface EquipmentTypeDao {
    @Query("SELECT * FROM equipment_types")
    suspend fun getAll(): List<EquipmentTypesDb>

    @Query("SELECT * FROM equipment_types WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): EquipmentTypesDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(equipmentTypes: List<EquipmentTypesDb>)
}