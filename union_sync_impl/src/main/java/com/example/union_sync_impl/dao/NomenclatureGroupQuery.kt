package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals


fun sqlNomenclatureGroupQuery(
    textQuery: String? = null,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM nomenclature_group"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "nomenclature_group",
            filter = buildList {
                textQuery?.let {
                    add("name" contains textQuery)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}