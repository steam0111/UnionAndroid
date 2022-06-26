package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals

fun sqlBranchesQuery(
    organizationId: String? = null,
    textQuery: String? = null,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM branches"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "branches",
            filter = buildList {
                organizationId?.let {
                    add("organizationId" isEquals organizationId)
                }
                textQuery?.let {
                    add("name" contains textQuery)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}