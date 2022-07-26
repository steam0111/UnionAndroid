package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlRegionQuery(
    organizationId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null,
    isFilterCount: Boolean = false
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM regions"
    } else {
        "SELECT * FROM regions"
    }
    return SimpleSQLiteQuery(
        mainQuery.getRegionsFilterPartQuery(
            organizationId,
            textQuery,
            updateDate
        )
    )
}

private fun String.getRegionsFilterPartQuery(
    organizationId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null
): String = addFilters(
    sqlTableFilters = SqlTableFilters(
        tableName = "regions",
        filter = buildList {
            organizationId?.let {
                add("organizationId" contains organizationId)
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