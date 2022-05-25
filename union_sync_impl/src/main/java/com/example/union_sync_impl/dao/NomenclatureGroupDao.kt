package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.union_sync_impl.entity.NomenclatureGroup

@Dao
interface NomenclatureGroupDao {
    @Query("SELECT * FROM nomenclature_group")
    suspend fun getAll(): List<NomenclatureGroup>

    @Insert
    suspend fun insertAll(vararg users: NomenclatureGroup)
}