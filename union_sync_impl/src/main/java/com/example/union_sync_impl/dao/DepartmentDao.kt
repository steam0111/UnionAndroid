package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.DepartmentDb

@Dao
interface DepartmentDao {
    @Query("SELECT * FROM departments")
    suspend fun getAll(): List<DepartmentDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(departments: List<DepartmentDb>)
}