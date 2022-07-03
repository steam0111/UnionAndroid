package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains

fun sqlOrderQuery(
    textQuery: String? = null,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM orders"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "orders",
            filter = buildList {
                textQuery?.let {
                    add("number" contains textQuery)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}