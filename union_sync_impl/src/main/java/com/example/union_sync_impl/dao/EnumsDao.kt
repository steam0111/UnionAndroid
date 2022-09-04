package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.EnumDb

@Dao
interface EnumsDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<EnumDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actions: List<EnumDb>)
}