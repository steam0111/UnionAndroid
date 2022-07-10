package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlDepartmentsQuery(
    organizationId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null,
    isFilterCount: Boolean = false
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM departments"
    } else {
        "SELECT * FROM departments"
    }
    return SimpleSQLiteQuery(
        mainQuery.getDepartmentsFilterPartQuery(
            organizationId,
            textQuery,
            updateDate
        )
    )
}

private fun String.getDepartmentsFilterPartQuery(
    organizationId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null
): String = addFilters(
    sqlTableFilters = SqlTableFilters(
        tableName = "departments",
        filter = buildList {
            organizationId?.let {
                add("organizationId" isEquals organizationId)
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