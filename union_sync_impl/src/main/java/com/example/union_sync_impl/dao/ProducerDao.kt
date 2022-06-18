package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.ProducerDb

@Dao
interface ProducerDao {

    @Query("SELECT * FROM producer")
    suspend fun getAll(): List<ProducerDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(producers: List<ProducerDb>)
}