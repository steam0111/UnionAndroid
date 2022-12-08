package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.more

fun sqlTerminalRemainsNumeratorQuery(
    limit: Long? = null,
    offset: Long? = null,
    updateDate: Long? = null,
    isNonCancel: Boolean = true,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM terminal_remains_numerator"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "terminal_remains_numerator",
            filter = buildList {
                if (isNonCancel) {
                    addNonCancelFilter()
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