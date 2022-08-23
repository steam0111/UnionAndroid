package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.ActionRecordDb
import com.example.union_sync_impl.entity.ActionRemainsRecordDb
import com.example.union_sync_impl.entity.DocumentReserveCount
import com.example.union_sync_impl.entity.TransitRemainsRecordDb

@Dao
interface TransitRemainsRecordDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<TransitRemainsRecordDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actionRecords: List<TransitRemainsRecordDb>)

    @Update(entity = TransitRemainsRecordDb::class)
    suspend fun update(listCounts: List<DocumentReserveCount>)
}