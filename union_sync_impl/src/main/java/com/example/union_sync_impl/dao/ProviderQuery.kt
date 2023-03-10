package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.more

fun sqlProviderQuery(
    textQuery: String? = null,
    updateDate: Long? = null,
    isNonCancel: Boolean = true,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM providers"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "providers",
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
    )

    return SimpleSQLiteQuery(query)
}