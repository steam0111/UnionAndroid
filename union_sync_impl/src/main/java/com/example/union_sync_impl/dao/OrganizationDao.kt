package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.OrganizationDb

@Dao
interface OrganizationDao {
    @Query("SELECT * FROM organizations")
    suspend fun getAll(): List<OrganizationDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(organizations: List<OrganizationDb>)
}