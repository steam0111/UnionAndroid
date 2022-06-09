package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.union_sync_impl.entity.location.LocationPath

@Dao
interface LocationPathDao {
    @Query("SELECT * FROM locationPath")
    suspend fun getAll(): List<LocationPath>

    @Insert
    suspend fun insertAll(vararg locationPaths: LocationPath)
}