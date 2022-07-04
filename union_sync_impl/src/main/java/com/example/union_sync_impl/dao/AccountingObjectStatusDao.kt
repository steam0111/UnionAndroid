package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.AccountingObjectStatusDb

@Dao
interface AccountingObjectStatusDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<AccountingObjectStatusDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(statuses: List<AccountingObjectStatusDb>)
}