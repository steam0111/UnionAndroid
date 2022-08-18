package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.EmployeeDb
import com.example.union_sync_impl.entity.FullEmployeeDb

@Dao
interface EmployeeDao {
    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<EmployeeDb>

    @RawQuery
    fun getCount(query: SupportSQLiteQuery): Long

    @Query(
        "SELECT employees.*," +
                "" +
                "structural.id AS structural_id, " +
                "structural.catalogItemName AS structural_catalogItemName, " +
                "structural.name AS structural_name, " +
                "structural.parentId AS structural_parentId " +
                "" +
                "FROM employees " +
                "LEFT JOIN structural ON employees.structuralId = structural.id " +
                "WHERE employees.id = :id LIMIT 1"
    )
    suspend fun getFullById(id: String): FullEmployeeDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(employees: List<EmployeeDb>)
}