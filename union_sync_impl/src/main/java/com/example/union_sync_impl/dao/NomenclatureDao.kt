package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.union_sync_impl.entity.Nomenclature

@Dao
interface NomenclatureDao {
    @Query("SELECT * FROM nomenclature")
    suspend fun getAll(): List<Nomenclature>

    @Insert
    suspend fun insertAll(vararg nomenclatures: Nomenclature)
}