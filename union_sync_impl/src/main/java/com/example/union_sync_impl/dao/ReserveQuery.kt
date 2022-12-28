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
    barcode: String? = null,
    hideZeroReserves: Boolean = false,
    nomenclatureId: String? = null
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
                "location.locationTypeId AS locations_locationTypeId, " +
                "" +
                "label_type.id AS label_type_id, " +
                "label_type.name AS label_type_name," +
                "label_type.description AS label_type_description," +
                "label_type.code as label_type_code," +
                "label_type.catalogItemName AS label_type_catalogItemName, " +
                "" +
                "nomenclature.id AS nomenclature_id, " +
                "nomenclature.catalogItemName AS nomenclature_catalogItemName, " +
                "nomenclature.name AS nomenclature_name, " +
                "nomenclature.number AS nomenclature_number, " +
                "nomenclature.nomenclatureGroupId AS nomenclature_nomenclatureGroupId " +
                "" +
                "FROM reserves " +
                "LEFT JOIN location ON reserves.locationId = location.id " +
                "LEFT JOIN label_type ON reserves.labelTypeId = label_type.id " +
                "LEFT JOIN nomenclature ON reserves.nomenclatureId = nomenclature.id " +
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
                barcode?.let {
                    add("barcodeValue" isEquals barcode)
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

                nomenclatureId?.let {
                    add("nomenclatureId" isEquals nomenclatureId)
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