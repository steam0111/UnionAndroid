package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "transit_record")
class TransitAccountingObjectRecordDb(
    id: String,
    cancel: Boolean? = false,
    val transitId: String?,
    val accountingObjectId: String?,
    insertDate: Long?,
    updateDate: Long?,
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