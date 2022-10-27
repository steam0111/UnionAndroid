package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(tableName = "counterparty")
class CounterpartyDb(
    id: String,
    cancel: Boolean? = false,
    override var catalogItemName: String,
    val name: String?,
    val actualAddress: String?,
    val legalAddress: String?,
    val inn: String?,
    val kpp: String?,
    val code: String?,
    insertDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : CatalogItemDb(
    id = id,
    cancel = cancel,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)