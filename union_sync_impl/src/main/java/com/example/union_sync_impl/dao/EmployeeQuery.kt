package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlEmployeeQuery(
    structuralId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null,
    isFilterCount: Boolean = false,
    offset: Long? = null,
    limit: Long? = null,
    isNonCancel: Boolean = true,
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM employees"
    } else {
        "SELECT * FROM employees"
    }

    val query = mainQuery.getEmployeesFilterPartQuery(
        structuralId, textQuery, updateDate, isNonCancel
    ).addPagination(limit = limit, offset = offset)

    return SimpleSQLiteQuery(query)
}

private fun String.getEmployeesFilterPartQuery(
    structuralId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null,
    isNonCancel: Boolean = true
): String = addFilters(
    sqlTableFilters = SqlTableFilters(
        tableName = "employees",
        filter = buildList {
            if (isNonCancel) {
                addNonCancelFilter()
            }
            structuralId?.let {
                add("structuralId" isEquals structuralId)
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