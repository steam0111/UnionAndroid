package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlTransitQuery(
    textQuery: String? = null,
    molId: String? = null,
    structuralFromId: String? = null,
    structuralToId: String? = null,
    receivingId: String? = null,
    updateDate: Long? = null,
    limit: Long? = null,
    offset: Long? = null,
    isFilterCount: Boolean = false
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM transit"
    } else {
        "SELECT transit.*," +
                "" +
                "molEmployees.id AS mol_id, " +
                "molEmployees.catalogItemName AS mol_catalogItemName, " +
                "molEmployees.firstname AS mol_firstname, " +
                "molEmployees.lastname AS mol_lastname, " +
                "molEmployees.patronymic AS mol_patronymic, " +
                "molEmployees.number AS mol_number, " +
                "molEmployees.nfc AS mol_nfc, " +
                "" +
                "receivingEmployees.id AS receiving_id, " +
                "receivingEmployees.catalogItemName AS receiving_catalogItemName, " +
                "receivingEmployees.firstname AS receiving_firstname, " +
                "receivingEmployees.lastname AS receiving_lastname, " +
                "receivingEmployees.patronymic AS receiving_patronymic, " +
                "receivingEmployees.number AS receiving_number, " +
                "receivingEmployees.nfc AS receiving_nfc " +
                "" +
                "FROM transit " +
                "LEFT JOIN employees molEmployees ON transit.molId = molEmployees.id " +
                "LEFT JOIN employees receivingEmployees ON transit.receivingId = receivingEmployees.id "
    }

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "transit",
            filter = buildList {
                textQuery?.let {
                    add("id" contains textQuery)
                }
                molId?.let {
                    add("responsibleId" isEquals molId)
                }
                receivingId?.let {
                    add("receivingId" isEquals receivingId)
                }
                structuralFromId?.let {
                    add("structuralFromId" isEquals structuralFromId)
                }
                structuralToId?.let {
                    add("structuralToId" isEquals structuralToId)
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