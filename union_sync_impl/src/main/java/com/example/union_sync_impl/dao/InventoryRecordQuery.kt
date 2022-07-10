package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.isEquals

fun sqlInventoryRecordQuery(
    inventoryId: String? = null,
    accountingObjectIds: List<String>? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM inventory_record"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "inventory_record",
            filter = buildList {
                inventoryId?.let {
                    add("inventoryId" isEquals inventoryId)
                }
                accountingObjectIds?.let {
                    add("accountingObjectId" isEquals accountingObjectIds)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}