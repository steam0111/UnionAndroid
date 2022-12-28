package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb
import java.math.BigDecimal

@Entity(tableName = "transit_remains_record")
class TransitRemainsRecordDb(
    id: String,
    cancel: Boolean? = false,
    val transitId: String?,
    val remainId: String?,
    val count: BigDecimal?,
    updateDate: Long?,
    insertDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : SyncItemDb(
    id = id,
    cancel = cancel,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)