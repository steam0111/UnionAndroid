package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.isNotEquals
import com.example.union_sync_impl.utils.more

fun sqlActionRemainsRecordQuery(
    actionId: String? = null,
    remainIds: List<String>? = null,
    updateDate: Long? = null,
    limit: Long? = null,
    offset: Long? = null,
    isNonCancel: Boolean = true,
    isFilterCount: Boolean = false,
): SimpleSQLiteQuery {
    val mainQuery = if(isFilterCount) {
        "SELECT COUNT(*) FROM action_remains_record"
    } else {
        "SELECT * FROM action_remains_record"
    }

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "action_remains_record",
            filter = buildList {
                if (isNonCancel) {
                    addNonCancelFilter()
                }
                actionId?.let {
                    add("actionId" isEquals actionId)
                }
                remainIds?.let {
                    add("remainId" isEquals remainIds)
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