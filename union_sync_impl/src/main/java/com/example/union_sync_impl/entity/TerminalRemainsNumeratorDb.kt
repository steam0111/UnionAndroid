package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "terminal_remains_numerator")
class TerminalRemainsNumeratorDb(
    id: String,
    val terminalPrefix: Int,
    val terminalId: String,
    val actualNumber: Int,
    insertDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
    cancel: Boolean? = false,
) : SyncItemDb(
    id = id,
    cancel = cancel,
    insertDate = insertDate,
    updateDate = updateDate,
    userInserted = userInserted,
    userUpdated = userUpdated
)