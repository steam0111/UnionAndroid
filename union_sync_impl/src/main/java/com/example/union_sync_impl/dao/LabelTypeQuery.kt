package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.*

fun sqlLabelTypeQuery(
    textQuery: String? = null,
    updateDate: Long? = null,
    isFilterCount: Boolean = false,
    offset: Long? = null,
    limit: Long? = null,
    isNonCancel: Boolean = true,
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM label_type"
    } else {
        "SELECT * FROM label_type"
    }
    return SimpleSQLiteQuery(
        mainQuery.getLabelTypeFilterPartQuery(
            textQuery,
            updateDate,
            isNonCancel,
        ).addPagination(limit = limit, offset = offset)
    )
}

private fun String.getLabelTypeFilterPartQuery(
    textQuery: String? = null,
    updateDate: Long? = null,
    isNonCancel: Boolean = true
): String = addFilters(
    sqlTableFilters = SqlTableFilters(
        tableName = "label_type",
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
).addOrder(fieldName = "name", order = Order.ASC)