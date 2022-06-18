package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.AccountingObjectDb

@Dao
interface AccountingObjectDao {
    @Query("SELECT * FROM accounting_objects")
    suspend fun getAll(): List<AccountingObjectDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objects: List<AccountingObjectDb>)
}