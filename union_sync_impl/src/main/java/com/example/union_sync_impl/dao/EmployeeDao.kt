package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.EmployeeDb
import com.example.union_sync_impl.entity.FullEmployeeDb

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    suspend fun getAll(): List<EmployeeDb>

    @Query(
        "SELECT employees.*," +
                "organizations.id AS organizations_id, " +
                "organizations.catalogItemName AS organizations_catalogItemName, " +
                "organizations.name AS organizations_name, " +
                "organizations.actualAddress AS organizations_actualAddress, " +
                "organizations.legalAddress AS organizations_legalAddress " +
                "" +
                "FROM employees " +
                "LEFT JOIN organizations ON employees.organizationId = organizations.id " +
                "WHERE employees.id = :id LIMIT 1"
    )
    suspend fun getFullById(id: String): FullEmployeeDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(employees: List<EmployeeDb>)
}