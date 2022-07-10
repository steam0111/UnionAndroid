package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlActionRecordQuery(
    actionId: String? = null,
    accountingObjectIds: List<String>? = null,
    updateDate: Long? = null,
    limit: Long? = null,
    offset: Long? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM action_record"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "action_record",
            filter = buildList {
                actionId?.let {
                    add("actionId" isEquals actionId)
                }
                accountingObjectIds?.let {
                    add("accountingObjectId" isEquals accountingObjectIds)
                }
                updateDate?.let {
                    add("updateDate" more updateDate)
                }
            }
        )
    ).addPagination(
        limit,
        offset
    )

    return SimpleSQLiteQuery(query)
}