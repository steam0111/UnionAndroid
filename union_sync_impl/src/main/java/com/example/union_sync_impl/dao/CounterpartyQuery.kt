package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.more

fun sqlCounterpartyQuery(
    textQuery: String? = null,
    updateDate: Long? = null,
    offset: Long? = null,
    limit: Long? = null,
    isNonCancel: Boolean = true,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM counterparty"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "counterparty",
            filter = buildList {
                if (isNonCancel) {
                    addNonCancelFilter()
                }
                textQuery?.let {
                    add("name" contains textQuery)
                }
                updateDate?.let {
                    add("updateDate" more updateDate)
                }
            }
        )
    ).addPagination(limit = limit, offset = offset)

    return SimpleSQLiteQuery(query)
}