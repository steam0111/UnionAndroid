package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlReserveQuery(
    organizationId: String? = null,
    molId: String? = null,
    structuralSubdivisionId: String? = null,
    nomenclatureGroupId: String? = null,
    orderId: String? = null,
    receptionItemCategoryId: String? = null,
    textQuery: String? = null,
    reservesIds: List<String>? = null,
    reservesShorts: List<ReserveShortSyncEntity>? = null,
    isFilterCount: Boolean = false,
    updateDate: Long? = null,
    locationIds: List<String?>? = null,
    limit: Long? = null,
    offset: Long? = null
): SimpleSQLiteQuery {

    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM reserves"
    } else {
        "SELECT reserves.*," +
                "" +
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId, " +
                "location.locationTypeId AS locations_locationTypeId " +
                "" +
                "FROM reserves " +
                "LEFT JOIN location ON reserves.locationId = location.id"
    }

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "reserves",
            filter = buildList {
                organizationId?.let {
                    add("businessUnitId" isEquals organizationId)
                }

                molId?.let {
                    add("molId" isEquals molId)
                }
                structuralSubdivisionId?.let {
                    add("structuralSubdivisionId" isEquals structuralSubdivisionId)
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
                reservesIds?.let {
                    add("id" isEquals reservesIds)
                }
                locationIds?.let {
                    add("locationId" isEquals locationIds)
                }
                textQuery?.let {
                    add("name" contains textQuery)
                }
                updateDate?.let {
                    add("updateDate" more updateDate)
                }
                reservesShorts?.let {
                    val names = it.map { it.name }
                    val nomenclatureIds = it.mapNotNull { it.nomenclatureId }
                    val shortLocationIds = it.mapNotNull { it.locationId }
                    val orderIds = it.mapNotNull { it.orderId }

                    add("name" isEquals names)
                    add("nomenclatureId" isEquals nomenclatureIds)
                    add("locationId" isEquals shortLocationIds)
                    add("orderId" isEquals orderIds)
                }
            }
        )
    ).addPagination(
        limit,
        offset
    )

    return SimpleSQLiteQuery(query)
}