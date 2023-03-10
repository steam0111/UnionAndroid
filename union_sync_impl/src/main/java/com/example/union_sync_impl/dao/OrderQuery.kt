package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.more

fun sqlOrderQuery(
    textQuery: String? = null,
    updateDate: Long? = null,
    isNonCancel: Boolean = true,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM orders"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "orders",
            filter = buildList {
                if (isNonCancel) {
                    addNonCancelFilter()
                }
                textQuery?.let {
                    add("number" contains textQuery)
                }
                updateDate?.let {
                    add("updateDate" more updateDate)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}