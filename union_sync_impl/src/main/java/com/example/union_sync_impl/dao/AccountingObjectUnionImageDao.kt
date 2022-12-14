package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.AccountingObjectUnionImageDb
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountingObjectUnionImageDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<AccountingObjectUnionImageDb>

    @Query("SELECT * FROM accounting_object_union_image WHERE accountingObjectId = :accountingObjectId")
    fun getAllFlow(accountingObjectId: String): Flow<List<AccountingObjectUnionImageDb>>

    @RawQuery
    fun getCount(query: SupportSQLiteQuery): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<AccountingObjectUnionImageDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AccountingObjectUnionImageDb)
}