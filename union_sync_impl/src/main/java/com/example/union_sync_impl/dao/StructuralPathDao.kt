package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.structural.StructuralPathDb

@Dao
interface StructuralPathDao {
    @Query("SELECT * FROM structuralPath")
    suspend fun getAll(): List<StructuralPathDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg structuralPathDbs: StructuralPathDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(structuralPathDbs: List<StructuralPathDb>)
}