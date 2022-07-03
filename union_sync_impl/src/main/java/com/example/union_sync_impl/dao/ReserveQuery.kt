package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals

fun sqlReserveQuery(
    organizationId: String? = null,
    molId: String? = null,
    structuralSubdivisionId: String? = null,
    nomenclatureId: String? = null,
    nomenclatureGroupId: String? = null,
    orderId: String? = null,
    receptionItemCategoryId: String? = null,
    textQuery: String? = null,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT reserves.*," +
            "" +
            "location.id AS locations_id, " +
            "location.catalogItemName AS locations_catalogItemName, " +
            "location.name AS locations_name, " +
            "location.parentId AS locations_parentId, " +
            "location.locationTypeId AS locations_locationTypeId " +
            "" +
            "FROM reserves " +
            "LEFT JOIN location ON reserves.locationId = location.id"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "reserves",
            filter = buildList {
                organizationId?.let {
                    add("organizationId" isEquals organizationId)
                }
                molId?.let {
                    add("molId" isEquals molId)
                }
                structuralSubdivisionId?.let {
                    add("structuralSubdivisionId" isEquals structuralSubdivisionId)
                }
                nomenclatureId?.let {
                    add("nomenclatureId" isEquals nomenclatureId)
                }
                nomenclatureGroupId?.let {
                    add("nomenclatureGroupId" isEquals nomenclatureGroupId)
                }
                orderId?.let {
                    add("orderId" isEquals orderId)
                }
                receptionItemCategoryId?.let {
                    add("receptionItemCategoryId" isEquals receptionItemCategoryId)
                }
                textQuery?.let {
                    add("name" contains textQuery)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}