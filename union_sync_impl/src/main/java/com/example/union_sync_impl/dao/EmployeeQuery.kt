package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.isEquals

fun sqlEmployeeQuery(
    organizationId: String? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM employees"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "employees",
            filter = buildList {
                organizationId?.let {
                    add("organizationId" isEquals organizationId)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}