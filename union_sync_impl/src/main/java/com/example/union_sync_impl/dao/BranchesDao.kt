package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.BranchesDb

@Dao
interface BranchesDao {
    @Query("SELECT * FROM branches")
    suspend fun getAll(): List<BranchesDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(branches: List<BranchesDb>)
}