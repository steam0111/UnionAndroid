package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.BranchesDb
import com.example.union_sync_impl.entity.FullBranchDb

@Dao
interface BranchesDao {
    @Query("SELECT * FROM branches")
    suspend fun getAll(): List<BranchesDb>

    @Query(
        "SELECT branches.*," +
                "organizations.id AS organizations_id, " +
                "organizations.catalogItemName AS organizations_catalogItemName, " +
                "organizations.name AS organizations_name, " +
                "organizations.actualAddress AS organizations_actualAddress, " +
                "organizations.legalAddress AS organizations_legalAddress, " +
                "organizations.comment AS organizations_comment, " +
                "organizations.kpp AS organizations_kpp, " +
                "organizations.inn AS organizations_inn " +
                "" +
                "FROM branches " +
                "LEFT JOIN organizations ON branches.organizationId = organizations.id " +
                "WHERE branches.id = :id LIMIT 1"
    )
    suspend fun getFullById(id: String): FullBranchDb


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(branches: List<BranchesDb>)
}