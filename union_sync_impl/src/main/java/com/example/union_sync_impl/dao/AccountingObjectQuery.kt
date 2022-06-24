package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery

fun sqlAccountingObjectQuery(
    organizationId: String? = null,
    exploitingId: String? = null,
    accountingObjectsIds: List<String>? = null,
    rfids: List<String>? = null,
    barcode: String? = null
): SimpleSQLiteQuery {
    val filters = mutableListOf<String>()

    if (organizationId != null) {
        filters.add("accounting_objects.organizationId = \'$organizationId\'")
    }

    if (exploitingId != null) {
        filters.add("accounting_objects.exploitingId = \'$exploitingId\'")
    }

    if (barcode != null) {
        filters.add("accounting_objects.barcodeValue = \'$barcode\'")
    }

    if (accountingObjectsIds != null) {
        val values = accountingObjectsIds.map { "\'$it\'" }
        filters.add("accounting_objects.id IN (${values.joinToString(",")})")
    }

    if (rfids != null) {
        val values = rfids.map { "\'$it\'"  }
        filters.add("accounting_objects.rfidValue IN (${values.joinToString(",")})")
    }

    val filterExpression = if (filters.isNotEmpty()) {
        "WHERE ${filters.joinToString(separator = " AND ")}"
    } else {
        ""
    }

    return SimpleSQLiteQuery(
        "SELECT accounting_objects.*," +
                "" +
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId " +
                "" +
                "FROM accounting_objects " +
                "LEFT JOIN location ON accounting_objects.locationId = location.id " +
                filterExpression
    )
}