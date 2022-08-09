package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlDocumentsQuery(
    textQuery: String? = null,
    molId: String? = null,
    exploitingId: String? = null,
    structuralId: String? = null,
    updateDate: Long? = null,
    limit: Long? = null,
    offset: Long? = null,
    isFilterCount: Boolean = false,
    type: String? = null
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM documents"
    } else {
        "SELECT documents.*," +
                "" +
                "molEmployees.id AS mol_id, " +
                "molEmployees.catalogItemName AS mol_catalogItemName, " +
                "molEmployees.firstname AS mol_firstname, " +
                "molEmployees.lastname AS mol_lastname, " +
                "molEmployees.patronymic AS mol_patronymic, " +
                "molEmployees.number AS mol_number, " +
                "molEmployees.nfc AS mol_nfc, " +
                "" +
                "exploitingEmployees.id AS exploiting_id, " +
                "exploitingEmployees.catalogItemName AS exploiting_catalogItemName, " +
                "exploitingEmployees.firstname AS exploiting_firstname, " +
                "exploitingEmployees.lastname AS exploiting_lastname, " +
                "exploitingEmployees.patronymic AS exploiting_patronymic, " +
                "exploitingEmployees.number AS exploiting_number, " +
                "exploitingEmployees.nfc AS exploiting_nfc, " +
                "" +
                "action_base.id AS action_bases_id, " +
                "action_base.name AS action_bases_name, " +
                "action_base.updateDate AS action_bases_updateDate " +
                "" +
                "FROM documents " +
                "LEFT JOIN employees molEmployees ON documents.molId = molEmployees.id " +
                "LEFT JOIN employees exploitingEmployees ON documents.exploitingId = exploitingEmployees.id " +
                "LEFT JOIN action_base ON documents.actionBaseId = action_base.id "
    }

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
                structuralId?.let {
                    add("structuralId" isEquals structuralId)
                }
                updateDate?.let {
                    add("updateDate" more updateDate)
                }
                type?.let {
                    add("documentType" isEquals type)
                }
            }
        )
    ).addPagination(
        limit,
        offset
    )

    return SimpleSQLiteQuery(query)
}