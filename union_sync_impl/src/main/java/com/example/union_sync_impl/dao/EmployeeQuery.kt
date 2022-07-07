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
    updateDate: Long? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM employees"

    val query = mainQuery.addFilters(
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
    return SimpleSQLiteQuery(query)
}