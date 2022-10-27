package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlTransitRemainsRecordQuery(
    transitId: String? = null,
    remainIds: List<String>? = null,
    updateDate: Long? = null,
    limit: Long? = null,
    offset: Long? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM transit_remains_record"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "transit_remains_record",
            filter = buildList {
                addNonCancelFilter()
                transitId?.let {
                    add("transitId" isEquals transitId)
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