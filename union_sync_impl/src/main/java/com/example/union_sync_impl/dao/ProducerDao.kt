package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.ProducerDb

@Dao
interface ProducerDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<ProducerDb>

    @Query("SELECT * FROM producer WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ProducerDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(producers: List<ProducerDb>)
}