package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.EnumDb

@Dao
interface EnumsDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<EnumDb>

    @Query("SELECT * FROM enums WHERE id is :id AND enumType = :enumType LIMIT 1")
    suspend fun getByCompoundId(id: String?, enumType: String): EnumDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actions: List<EnumDb>)
}