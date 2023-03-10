package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.Order
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addOrder
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlLocationsQuery(
    parentId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null,
    isNonCancel: Boolean = true,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM location"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "location",
            filter = buildList {
                if (isNonCancel) {
                    addNonCancelFilter()
                }
                add("parentId" isEquals parentId)
                textQuery?.let {
                    add("name" contains textQuery)
                }
                updateDate?.let {
                    add("updateDate" more updateDate)
                }
            }
        )
    ).addOrder("name", Order.ASC)

    return SimpleSQLiteQuery(query)
}