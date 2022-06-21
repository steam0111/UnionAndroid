package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.NomenclatureGroupDb

@Dao
interface NomenclatureGroupDao {
    @Query("SELECT * FROM nomenclature_group")
    suspend fun getAll(): List<NomenclatureGroupDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: NomenclatureGroupDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(nomenclatureGroups: List<NomenclatureGroupDb>)
}