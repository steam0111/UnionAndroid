package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.AccountingObjectUnionImageDb

@Dao
interface AccountingObjectUnionImageDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<AccountingObjectUnionImageDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<AccountingObjectUnionImageDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AccountingObjectUnionImageDb)
}