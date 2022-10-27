package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains

fun sqlEquipmentTypeQuery(
    textQuery: String? = null,
    offset: Long? = null,
    limit: Long? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM equipment_types"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "equipment_types",
            filter = buildList {
                addNonCancelFilter()
                textQuery?.let {
                    add("name" contains textQuery)
                }
            }
        )
    ).addPagination(limit = limit, offset = offset)

    return SimpleSQLiteQuery(query)
}