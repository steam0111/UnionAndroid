package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlDocumentsQuery(
    textQuery: String? = null,
    molId: String? = null,
    exploitingId: String? = null,
    organizationId: String? = null,
    updateDate: Long? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT documents.*," +
            "" +
            "organizations.id AS organizations_id, " +
            "organizations.catalogItemName AS organizations_catalogItemName, " +
            "organizations.name AS organizations_name, " +
            "organizations.actualAddress AS organizations_actualAddress, " +
            "organizations.legalAddress AS organizations_legalAddress, " +
            "" +
            "molEmployees.id AS mol_id, " +
            "molEmployees.catalogItemName AS mol_catalogItemName, " +
            "molEmployees.firstname AS mol_firstname, " +
            "molEmployees.lastname AS mol_lastname, " +
            "molEmployees.patronymic AS mol_patronymic, " +
            "molEmployees.organizationId AS mol_organizationId, " +
            "molEmployees.number AS mol_number, " +
            "molEmployees.nfc AS mol_nfc, " +
            "" +
            "exploitingEmployees.id AS exploiting_id, " +
            "exploitingEmployees.catalogItemName AS exploiting_catalogItemName, " +
            "exploitingEmployees.firstname AS exploiting_firstname, " +
            "exploitingEmployees.lastname AS exploiting_lastname, " +
            "exploitingEmployees.patronymic AS exploiting_patronymic, " +
            "exploitingEmployees.organizationId AS exploiting_organizationId, " +
            "exploitingEmployees.number AS exploiting_number, " +
            "exploitingEmployees.nfc AS exploiting_nfc " +
            "" +
            "FROM documents " +
            "LEFT JOIN organizations ON documents.organizationId = organizations.id " +
            "LEFT JOIN employees molEmployees ON documents.molId = molEmployees.id " +
            "LEFT JOIN employees exploitingEmployees ON documents.exploitingId = exploitingEmployees.id "

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "documents",
            filter = buildList {
                textQuery?.let {
                    add("id" contains textQuery)
                }
                molId?.let {
                    add("molId" isEquals molId)
                }
                exploitingId?.let {
                    add("exploitingId" isEquals exploitingId)
                }
                organizationId?.let {
                    add("organizationId" isEquals organizationId)
                }
                updateDate?.let {
                    add("updateDate" more updateDate)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}