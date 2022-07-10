package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.isEquals

fun sqlActionRemainsRecordQuery(
    actionId: String? = null,
    remainIds: List<String>? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM action_remains_record"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "action_remains_record",
            filter = buildList {
                actionId?.let {
                    add("actionId" isEquals actionId)
                }
                remainIds?.let {
                    add("remainId" isEquals remainIds)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}