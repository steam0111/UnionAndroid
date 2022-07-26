package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlAccountingObjectQuery(
    organizationId: String? = null,
    exploitingId: String? = null,
    rfids: List<String>? = null,
    barcode: String? = null,
    molId: String? = null,
    departmentId: String? = null,
    producerId: String? = null,
    equipmentTypeId: String? = null,
    providerId: String? = null,
    statusId: String? = null,
    textQuery: String? = null,
    accountingObjectsIds: List<String>? = null,
    updateDate: Long? = null,
    isFilterCount: Boolean = false,
    limit: Long? = null,
    offset: Long? = null,
    locationIds: List<String?>? = null
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM accounting_objects"
    } else {
        "SELECT accounting_objects.*," +
                "" +
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId, " +
                "location.locationTypeId AS locations_locationTypeId " +
                "" +
                "FROM accounting_objects " +
                "LEFT JOIN location ON accounting_objects.locationId = location.id"
    }

    val query = mainQuery.getAccountingObjectsFilterPartQuery(
        organizationId = organizationId,
        exploitingId = exploitingId,
        producerId = producerId,
        providerId = providerId,
        equipmentTypeId = equipmentTypeId,
        departmentId = departmentId,
        molId = molId,
        rfids = rfids,
        barcode = barcode,
        statusId = statusId,
        accountingObjectsIds = accountingObjectsIds,
        textQuery = textQuery,
        updateDate = updateDate,
        locationIds = locationIds
    ).addPagination(
        limit,
        offset
    )

    return SimpleSQLiteQuery(query)
}

private fun String.getAccountingObjectsFilterPartQuery(
    organizationId: String? = null,
    exploitingId: String? = null,
    rfids: List<String>? = null,
    barcode: String? = null,
    molId: String? = null,
    departmentId: String? = null,
    producerId: String? = null,
    equipmentTypeId: String? = null,
    providerId: String? = null,
    statusId: String? = null,
    textQuery: String? = null,
    accountingObjectsIds: List<String>? = null,
    updateDate: Long? = null,
    locationIds: List<String?>? = null
): String = addFilters(
    sqlTableFilters = SqlTableFilters(
        tableName = "accounting_objects",
        filter = buildList {
            organizationId?.let {
                add("organizationId" isEquals organizationId)
            }
            exploitingId?.let {
                add("exploitingId" isEquals exploitingId)
            }
            accountingObjectsIds?.let {
                add("id" isEquals accountingObjectsIds)
            }
            rfids?.let {
                add("rfidValue" isEquals rfids)
            }
            barcode?.let {
                add("barcodeValue" isEquals barcode)
            }
            molId?.let {
                add("molId" isEquals molId)
            }
            departmentId?.let {
                add("departmentId" isEquals departmentId)
            }
            producerId?.let {
                add("producerId" isEquals producerId)
            }
            equipmentTypeId?.let {
                add("equipmentTypeId" isEquals equipmentTypeId)
            }
            providerId?.let {
                add("providerId" isEquals providerId)
            }
            statusId?.let {
                add("statusId" isEquals statusId)
            }
            textQuery?.let {
                add("name" contains textQuery)
            }
            updateDate?.let {
                add("updateDate" more updateDate)
            }
            locationIds?.let {
                add("locationId" isEquals locationIds)
            }
        }
    )
)