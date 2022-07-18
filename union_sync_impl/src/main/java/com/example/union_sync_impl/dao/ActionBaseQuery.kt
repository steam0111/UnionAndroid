package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains

fun sqlActionBaseQuery(
    textQuery: String? = null,
    isFilterCount: Boolean = false
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM action_base"
    } else {
        "SELECT * FROM action_base"
    }
    return SimpleSQLiteQuery(mainQuery.getActionBaseFilterPartQuery(textQuery))
}

private fun String.getActionBaseFilterPartQuery(
    textQuery: String? = null
): String = addFilters(
    sqlTableFilters = SqlTableFilters(
        tableName = "action_base",
        filter = buildList {
            textQuery?.let {
                add("name" contains textQuery)
            }
        }
    )
)