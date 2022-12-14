package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlAccountingObjectUnionImageQuery(
    accountingObjectId: String? = null,
    updateDate: Long? = null,
    offset: Long? = null,
    limit: Long? = null,
    isNonCancel: Boolean = true,
    isFilterCount: Boolean = false,
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM accounting_object_union_image"
    } else {
        "SELECT * FROM accounting_object_union_image"
    }
    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "accounting_object_union_image",
            filter = buildList {
                if (isNonCancel) {
                    addNonCancelFilter()
                }
                accountingObjectId?.let {
                    add("accountingObjectId" isEquals accountingObjectId)
                }
                updateDate?.let {
                    add("updateDate" more updateDate)
                }
            }
        )
    ).addPagination(limit = limit, offset = offset)

    return SimpleSQLiteQuery(query)

}