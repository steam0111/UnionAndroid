package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals

fun sqlAccountingObjectQuery(
    organizationId: String? = null,
    exploitingId: String? = null,
    accountingObjectsIds: List<String>? = null,
    rfids: List<String>? = null,
    barcode: String? = null,
    textQuery: String? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT accounting_objects.*," +
            "" +
            "location.id AS locations_id, " +
            "location.catalogItemName AS locations_catalogItemName, " +
            "location.name AS locations_name, " +
            "location.parentId AS locations_parentId, " +
            "location.locationTypeId AS locations_locationTypeId " +
            "" +
            "FROM accounting_objects " +
            "LEFT JOIN location ON accounting_objects.locationId = location.id"

    val query = mainQuery.addFilters(
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
                textQuery?.let {
                    add("name" contains textQuery)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}