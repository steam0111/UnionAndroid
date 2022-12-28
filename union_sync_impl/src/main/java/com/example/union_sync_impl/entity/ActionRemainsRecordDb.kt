package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb
import java.math.BigDecimal

@Entity(tableName = "action_remains_record")
class ActionRemainsRecordDb(
    id: String,
    cancel: Boolean? = false,
    val actionId: String,
    val remainId: String,
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
) {
    fun copy(
        id: String = this.id,
        cancel: Boolean? = this.cancel,
        actionId: String = this.actionId,
        remainId: String = this.remainId,
        count: BigDecimal? = this.count,
        updateDate: Long? = this.updateDate,
        insertDate: Long? = this.insertDate,
        userInserted: String? = this.userInserted,
        userUpdated: String? = this.userUpdated
    ): ActionRemainsRecordDb {
        return ActionRemainsRecordDb(
            id = id,
            cancel = cancel,
            actionId = actionId,
            remainId = remainId,
            count = count,
            updateDate = updateDate,
            insertDate = insertDate,
            userInserted = userInserted,
            userUpdated = userUpdated,
        )
    }
}
