package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.NetworkSyncDb

@Dao
interface NetworkSyncDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(networkSyncDb: NetworkSyncDb)

    @Query("SELECT * FROM network_sync LIMIT 1")
    suspend fun getNetworkSync(): NetworkSyncDb?
}