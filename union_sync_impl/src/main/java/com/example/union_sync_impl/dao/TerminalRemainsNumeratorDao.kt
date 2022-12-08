package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.TerminalRemainsNumeratorDb

@Dao
interface TerminalRemainsNumeratorDao {

    @RawQuery
    suspend fun getAllRemainsNumerators(query: SupportSQLiteQuery): List<TerminalRemainsNumeratorDb>

    @Query("SELECT * FROM terminal_remains_numerator WHERE id = :id LIMIT 1")
    suspend fun getRemainNumeratorById(id: String): TerminalRemainsNumeratorDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<TerminalRemainsNumeratorDb>)
}