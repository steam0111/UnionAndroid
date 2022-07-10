package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.isEquals

fun sqlDocumentReserveCountQuery(
    reserveIds: List<String>? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM document_reserve_count"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "document_reserve_count",
            filter = buildList {
                reserveIds?.let {
                    add("id" isEquals reserveIds)
                }
            }
        )
    )
    return SimpleSQLiteQuery(query)
}