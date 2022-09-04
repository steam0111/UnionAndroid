package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals

fun sqlEnumsQuery(
    enumType: String,
    id: String? = null,
    name: String? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM enums"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "enums",
            filter = buildList {
                add("enumType" isEquals enumType)
                id?.let {
                    add("id" isEquals id)
                }
                name?.let {
                    add("name" contains name)
                }
            }
        )
    )
    return SimpleSQLiteQuery(query)
}