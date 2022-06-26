package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.DepartmentDb
import com.example.union_sync_impl.entity.FullDepartmentDb

@Dao
interface DepartmentDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<DepartmentDb>

    @Query(
        "SELECT departments.*," +
                "organizations.id AS organizations_id, " +
                "organizations.catalogItemName AS organizations_catalogItemName, " +
                "organizations.name AS organizations_name, " +
                "organizations.actualAddress AS organizations_actualAddress, " +
                "organizations.legalAddress AS organizations_legalAddress " +
                "" +
                "FROM departments " +
                "LEFT JOIN organizations ON departments.organizationId = organizations.id " +
                "WHERE departments.id = :id LIMIT 1"
    )
    suspend fun getFullById(id: String): FullDepartmentDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(departments: List<DepartmentDb>)
}