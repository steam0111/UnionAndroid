package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.EquipmentTypesDb

@Dao
interface EquipmentTypeDao {
    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<EquipmentTypesDb>

    @Query("SELECT * FROM equipment_types WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): EquipmentTypesDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(equipmentTypes: List<EquipmentTypesDb>)
}