package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.union_sync_impl.entity.Nomenclature

@Dao
interface NomenclatureDao {
    @Insert
    suspend fun insertAll(vararg nomenclatures: Nomenclature)
}