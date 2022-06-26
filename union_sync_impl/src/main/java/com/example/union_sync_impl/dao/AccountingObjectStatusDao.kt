package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.AccountingObjectStatusDb

@Dao
interface AccountingObjectStatusDao {

    @Query("SELECT * FROM statuses")
    fun getAll(): List<AccountingObjectStatusDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(statuses: List<AccountingObjectStatusDb>)
}