package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains

fun sqlOrganizationQuery(
    textQuery: String? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM organizations"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "organizations",
            filter = buildList {
                textQuery?.let {
                    add("name" contains textQuery)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}