package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.NomenclatureGroupDb

@Dao
interface NomenclatureGroupDao {
    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<NomenclatureGroupDb>

    @Query("SELECT * FROM nomenclature_group WHERE id = :nomenclatureGroupId LIMIT 1")
    suspend fun getById(nomenclatureGroupId: String): NomenclatureGroupDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: NomenclatureGroupDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(nomenclatureGroups: List<NomenclatureGroupDb>)
}