package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains

fun sqlInventoryBaseQuery(
    textQuery: String? = null,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM inventory_base"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "inventory_base",
            filter = buildList {
                textQuery?.let {
                    add("name" contains textQuery)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}