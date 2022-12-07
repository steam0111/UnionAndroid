package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.TerminalInfoDb

@Dao
interface TerminalInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(terminalInfoDb: TerminalInfoDb)

    @Query("SELECT * FROM terminal_info LIMIT 1")
    suspend fun getTerminalInfo(): TerminalInfoDb?
}