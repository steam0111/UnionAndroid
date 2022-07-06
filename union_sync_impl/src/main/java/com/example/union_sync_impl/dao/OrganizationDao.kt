package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.FullOrganizationDb
import com.example.union_sync_impl.entity.OrganizationDb

@Dao
interface OrganizationDao {
    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<OrganizationDb>

    @Query(
        "SELECT organizations.*," +
                "" +
                "employees.id AS employees_id, " +
                "employees.catalogItemName AS employees_catalogItemName, " +
                "employees.firstname AS employees_firstname, " +
                "employees.lastname AS employees_lastname, " +
                "employees.patronymic AS employees_patronymic, " +
                "employees.organizationId AS employees_organizationId, " +
                "employees.number AS employees_number, " +
                "employees.nfc AS employees_nfc " +
                "FROM organizations " +
                "LEFT JOIN employees ON organizations.employeeId = employees.id " +
                "WHERE organizations.id = :id LIMIT 1 "
    )
    suspend fun getFullById(id: String): FullOrganizationDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(organizations: List<OrganizationDb>)
}