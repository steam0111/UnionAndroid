package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.contains

fun sqlReceptionItemCategoryQuery(
    textQuery: String? = null,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM reception_item_category"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "reception_item_category",
            filter = buildList {
                addNonCancelFilter()
                textQuery?.let {
                    add("name" contains textQuery)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}