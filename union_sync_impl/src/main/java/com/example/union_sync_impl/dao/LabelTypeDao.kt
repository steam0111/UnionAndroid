package com.example.union_sync_impl.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.LabelTypeDb

@Dao
interface LabelTypeDao {

    @RawQuery
    suspend fun getLabelTypes(query: SupportSQLiteQuery): List<LabelTypeDb>

    @Query("SELECT * FROM label_type WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): LabelTypeDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<LabelTypeDb>)
}