package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.NomenclatureDb

@Dao
interface NomenclatureDao {
    @Query("SELECT * FROM nomenclature")
    suspend fun getAll(): List<NomenclatureDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg nomenclatureDbs: NomenclatureDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(nomenclatureDbs: List<NomenclatureDb>)
}