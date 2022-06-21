package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.EmployeeDb

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    suspend fun getAll(): List<EmployeeDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(employees: List<EmployeeDb>)
}