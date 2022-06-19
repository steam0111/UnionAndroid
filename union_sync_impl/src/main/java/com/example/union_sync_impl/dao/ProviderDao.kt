package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.ProviderDb

@Dao
interface ProviderDao {

    @Query("SELECT * FROM providers")
    suspend fun getAll(): List<ProviderDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(providers: List<ProviderDb>)
}