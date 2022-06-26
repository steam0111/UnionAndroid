package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.CounterpartyDb

@Dao
interface CounterpartyDao {
    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<CounterpartyDb>

    @Query("SELECT * FROM counterparty WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): CounterpartyDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(organizations: List<CounterpartyDb>)
}