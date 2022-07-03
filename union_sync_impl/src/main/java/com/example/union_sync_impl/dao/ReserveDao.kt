package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.FullReserve
import com.example.union_sync_impl.entity.ReserveDb

@Dao
interface ReserveDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<FullReserve>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reserves: List<ReserveDb>)
}