package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.FullRegionDb
import com.example.union_sync_impl.entity.RegionDb

@Dao
interface RegionDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<RegionDb>

    @Query("SELECT * FROM regions WHERE id = :id LIMIT 1")
    suspend fun getFullById(id: String): FullRegionDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(departments: List<RegionDb>)
}