package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.union_sync_api.entity.AccountingObjectInfoSyncEntity
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "inventories")
class InventoryDb(
    id: String = "",
    updateDate: Long?,
    val organizationId: String,
    val employeeId: String?,
    val accountingObjectsIds: List<AccountingObjectInfoSyncEntity>,
    val locationIds: List<String>? = listOf(),
    val date: Long
) : SyncItemDb(id, updateDate)