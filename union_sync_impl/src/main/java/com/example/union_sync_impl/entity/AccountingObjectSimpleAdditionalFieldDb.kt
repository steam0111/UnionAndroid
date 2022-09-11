package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "accountingObjectSimpleAdditionalFieldValue")
class AccountingObjectSimpleAdditionalFieldDb(
    id: String,
    insertDate: Long?,
    updateDate: Long?,
    userUpdated: String?,
    userInserted: String?,
    override var catalogItemName: String,
    val value: String?,
    val simpleAdditionalFieldId: String?,
    val accountingObjectId: String?
) : CatalogItemDb(
    id = id,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)