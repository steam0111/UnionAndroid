package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.isNotEquals
import com.example.union_sync_impl.utils.more

fun sqlAccountingObjectQuery(
    exploitingId: String? = null,
    rfids: List<String>? = null,
    barcode: String? = null,
    molId: String? = null,
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
    locationIds: List<String?>? = null,
    structuralIds: List<String?>? = null,
    serialNumber: String? = null,
    isShowUtilised: Boolean = true
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM accounting_objects"
    } else {
        "SELECT accounting_objects.*," +
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
                "FROM accounting_objects " +
                "LEFT JOIN location ON accounting_objects.locationId = location.id " +
                "LEFT JOIN structural ON accounting_objects.structuralId = structural.id "
    }

    val query = mainQuery.getAccountingObjectsFilterPartQuery(
        exploitingId = exploitingId,
        producerId = producerId,
        providerId = providerId,
        equipmentTypeId = equipmentTypeId,
        structuralIds = structuralIds,
        molId = molId,
        rfids = rfids,
        barcode = barcode,
        statusId = statusId,
        accountingObjectsIds = accountingObjectsIds,
        textQuery = textQuery,
        updateDate = updateDate,
        locationIds = locationIds,
        isShowUtilised = isShowUtilised,
        serialNumber = serialNumber
    ).addPagination(
        limit,
        offset
    )

    return SimpleSQLiteQuery(query)
}

private fun String.getAccountingObjectsFilterPartQuery(
    exploitingId: String? = null,
    rfids: List<String>? = null,
    barcode: String? = null,
    molId: String? = null,
    producerId: String? = null,
    equipmentTypeId: String? = null,
    providerId: String? = null,
    statusId: String? = null,
    textQuery: String? = null,
    accountingObjectsIds: List<String>? = null,
    updateDate: Long? = null,
    locationIds: List<String?>? = null,
    structuralIds: List<String?>? = null,
    isShowUtilised: Boolean = true,
    serialNumber: String? = null
): String = addFilters(
    sqlTableFilters = SqlTableFilters(
        tableName = "accounting_objects",
        filter = buildList {
            addNonCancelFilter()
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
                add(listOf("name", "inventoryNumber", "factoryNumber") contains textQuery)
            }
            updateDate?.let {
                add("updateDate" more updateDate)
            }
            locationIds?.let {
                add("locationId" isEquals locationIds)
            }
            structuralIds?.let {
                add("structuralId" isEquals structuralIds)
            }
            serialNumber?.let {
                add("factoryNumber" isEquals serialNumber)
            }
            if (!isShowUtilised && statusId == null) {
                add("statusId" isNotEquals "WRITTEN_OFF")
            }
        }
    )
)