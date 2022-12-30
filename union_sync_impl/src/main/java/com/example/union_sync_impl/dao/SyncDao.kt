package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.LabelTypeDb

@Dao
interface SyncDao {

    @RawQuery
    suspend fun removeDeletedItems(query: SupportSQLiteQuery): Boolean?
}