package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.isEquals

fun sqlRemoveDeletedItemsQuery(
    tableName: String,
    ids: List<String>
): SimpleSQLiteQuery {
    val mainQuery = "DELETE FROM $tableName"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = tableName,
            filter = buildList {
                add("id" isEquals ids)
            }
        )
    )

    return SimpleSQLiteQuery(query)
}