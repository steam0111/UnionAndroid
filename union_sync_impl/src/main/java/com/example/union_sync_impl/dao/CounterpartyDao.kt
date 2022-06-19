package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.CounterpartyDb

@Dao
interface CounterpartyDao {
    @Query("SELECT * FROM counterparty")
    suspend fun getAll(): List<CounterpartyDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(organizations: List<CounterpartyDb>)
}