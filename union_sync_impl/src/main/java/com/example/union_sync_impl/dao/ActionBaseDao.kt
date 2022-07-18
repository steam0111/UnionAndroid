package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.ActionBaseDb

@Dao
interface ActionBaseDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<ActionBaseDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actions: List<ActionBaseDb>)
}