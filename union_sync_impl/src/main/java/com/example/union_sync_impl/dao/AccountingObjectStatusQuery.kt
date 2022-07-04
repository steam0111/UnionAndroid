package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals

fun sqlAccountingObjectStatusQuery(
    id: String? = null,
    name: String? = null,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM statuses"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "statuses",
            filter = buildList {
                id?.let {
                    add("id" isEquals id)
                }
                name?.let {
                    add("name" isEquals name)
                }
            }
        )
    )
    return SimpleSQLiteQuery(query)
}