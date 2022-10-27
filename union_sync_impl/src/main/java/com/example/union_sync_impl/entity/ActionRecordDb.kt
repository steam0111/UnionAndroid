package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "action_record")
class ActionRecordDb(
    id: String,
    cancel: Boolean? = false,
    val actionId: String,
    val accountingObjectId: String,
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
) {
    fun copy(
        id: String = this.id,
        cancel: Boolean? = this.cancel,
        actionId: String = this.actionId,
        accountingObjectId: String = this.accountingObjectId,
        updateDate: Long? = this.updateDate,
        insertDate: Long? = this.insertDate,
        userInserted: String? = this.userInserted,
        userUpdated: String? = this.userUpdated
    ): ActionRecordDb {
        return ActionRecordDb(
            id = id,
            cancel = cancel,
            actionId = actionId,
            updateDate = updateDate,
            insertDate = insertDate,
            userInserted = userInserted,
            userUpdated = userUpdated,
            accountingObjectId = accountingObjectId
        )
    }
}