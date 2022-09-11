package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlNomenclatureQuery(
    nomenclatureGroupId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null,
    isFilterCount: Boolean = false,
    offset: Long? = null,
    limit: Long? = null
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM nomenclature"
    } else {
        "SELECT * FROM nomenclature"
    }
    return SimpleSQLiteQuery(
        mainQuery.getNomenclatureFilterPartQuery(
            nomenclatureGroupId,
            textQuery,
            updateDate
        ).addPagination(limit = limit, offset = offset)
    )
}

private fun String.getNomenclatureFilterPartQuery(
    nomenclatureGroupId: String? = null,
    textQuery: String? = null,
    updateDate: Long? = null
): String = addFilters(
    sqlTableFilters = SqlTableFilters(
        tableName = "nomenclature",
        filter = buildList {
            nomenclatureGroupId?.let {
                add("nomenclatureGroupId" isEquals nomenclatureGroupId)
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