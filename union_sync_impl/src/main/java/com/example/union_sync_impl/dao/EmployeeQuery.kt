package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlEmployeeQuery(
    organizationId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null,
    isFilterCount: Boolean = false
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM employees"
    } else {
        "SELECT * FROM employees"
    }

    val query = mainQuery.getEmployeesFilterPartQuery(
        organizationId, textQuery, updateDate
    )
    return SimpleSQLiteQuery(query)
}

private fun String.getEmployeesFilterPartQuery(
    organizationId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null
): String = addFilters(
    sqlTableFilters = SqlTableFilters(
        tableName = "employees",
        filter = buildList {
            organizationId?.let {
                add("organizationId" isEquals organizationId)
            }
            textQuery?.let {
                add(
                    listOf(
                        "firstname",
                        "lastname",
                        "patronymic"
                    ) contains textQuery
                )
            }
            updateDate?.let {
                add("updateDate" more updateDate)
            }
        }
    )
)