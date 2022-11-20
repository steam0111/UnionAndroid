package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlReserveQuery(
    structuralIds: List<String?>? = null,
    molId: String? = null,
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
    offset: Long? = null,
    isNonCancel: Boolean = true,
    hideZeroReserves: Boolean = false
): SimpleSQLiteQuery {
    val name = textQuery?.ifEmpty { null }
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM reserves"
    } else {
        "SELECT reserves.*," +
                "" +
                "structural.id AS structural_id, " +
                "structural.catalogItemName AS structural_catalogItemName, " +
                "structural.name AS structural_name, " +
                "structural.parentId AS structural_parentId, " +
                "" +
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId, " +
                "location.locationTypeId AS locations_locationTypeId " +
                "" +
                "FROM reserves " +
                "LEFT JOIN location ON reserves.locationId = location.id " +
                "LEFT JOIN structural ON reserves.structuralId = structural.id "
    }

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "reserves",
            filter = buildList {
                if (isNonCancel) {
                    addNonCancelFilter()
                }
                structuralIds?.let {
                    add("structuralId" isEquals structuralIds)
                }
                molId?.let {
                    add("molId" isEquals molId)
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
                name?.let {
                    add("name" contains name)
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
                if (hideZeroReserves) {
                    add("count" more 0)
                }
            }
        )
    ).addPagination(
        limit,
        offset
    )

    return SimpleSQLiteQuery(query)
}

fun sqlReserveSimpledQuery(
    limit: Long? = null,
    offset: Long? = null,
    updateDate: Long? = null,
    isNonCancel: Boolean = true,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM reserves"

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "reserves",
            filter = buildList {
                if (isNonCancel) {
                    addNonCancelFilter()
                }
                updateDate?.let {
                    add("updateDate" more updateDate)
                }
            }
        )
    ).addPagination(
        limit,
        offset
    )

    return SimpleSQLiteQuery(query)
}