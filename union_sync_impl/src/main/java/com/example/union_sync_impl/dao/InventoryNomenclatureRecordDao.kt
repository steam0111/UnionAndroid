package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.InventoryNomenclatureRecordDb

@Dao
interface InventoryNomenclatureRecordDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<InventoryNomenclatureRecordDb>

    @RawQuery
    fun getCount(query: SupportSQLiteQuery): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actionRecords: List<InventoryNomenclatureRecordDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actionRecord: InventoryNomenclatureRecordDb)
}