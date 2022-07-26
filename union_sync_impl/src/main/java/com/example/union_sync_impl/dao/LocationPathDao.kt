package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.location.LocationPathDb

@Dao
interface LocationPathDao {
    @Query("SELECT * FROM locationPath")
    suspend fun getAll(): List<LocationPathDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg locationPathDbs: LocationPathDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locationPathDbs: List<LocationPathDb>)
}