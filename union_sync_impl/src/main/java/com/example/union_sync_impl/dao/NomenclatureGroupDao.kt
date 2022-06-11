package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.union_sync_impl.entity.NomenclatureGroupDb

@Dao
interface NomenclatureGroupDao {
    @Query("SELECT * FROM nomenclature_group")
    suspend fun getAll(): List<NomenclatureGroupDb>

    @Insert
    suspend fun insertAll(vararg users: NomenclatureGroupDb)

    @Insert
    suspend fun insertAll(nomenclatureGroups: List<NomenclatureGroupDb>)
}