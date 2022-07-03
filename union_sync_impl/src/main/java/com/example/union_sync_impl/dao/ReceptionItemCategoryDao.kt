package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.ReceptionItemCategoryDb

@Dao
interface ReceptionItemCategoryDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<ReceptionItemCategoryDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(receptionCategoryItems: List<ReceptionItemCategoryDb>)
}