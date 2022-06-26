package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.isEquals

fun sqlNomenclatureQuery(
    nomenclatureGroupId: String? = null,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM nomenclature"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "nomenclature",
            filter = buildList {
                nomenclatureGroupId?.let {
                    add("nomenclatureGroupId" isEquals nomenclatureGroupId)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}