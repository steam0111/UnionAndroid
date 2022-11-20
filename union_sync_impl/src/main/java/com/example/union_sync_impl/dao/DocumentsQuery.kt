package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.addNonCancelFilter
import com.example.union_sync_impl.utils.addPagination
import com.example.union_sync_impl.utils.contains
import com.example.union_sync_impl.utils.isEquals
import com.example.union_sync_impl.utils.more

fun sqlDocumentsQuery(
        textQuery: String? = null,
        molId: String? = null,
        exploitingId: String? = null,
        structuralFromId: String? = null,
        structuralToId: String? = null,
        updateDate: Long? = null,
        limit: Long? = null,
        offset: Long? = null,
        isFilterCount: Boolean = false,
        type: String? = null,
        isNonCancel: Boolean = true,
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
                "exploitingEmployees.nfc AS exploiting_nfc " +
                "" +
                "FROM documents " +
                "LEFT JOIN employees molEmployees ON documents.molId = molEmployees.id " +
                "LEFT JOIN employees exploitingEmployees ON documents.exploitingId = exploitingEmployees.id "
    }

    val query = mainQuery.addFilters(
            sqlTableFilters = SqlTableFilters(
                    tableName = "documents",
                    filter = buildList {
                        if (isNonCancel) {
                            addNonCancelFilter()
                        }
                        textQuery?.let {
                            add("id" contains textQuery)
                        }
                        molId?.let {
                            add("molId" isEquals molId)
                        }
                        exploitingId?.let {
                            add("exploitingId" isEquals exploitingId)
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

fun sqlDocumentsSimpledQuery(
        limit: Long? = null,
        offset: Long? = null,
        updateDate: Long? = null,
        isNonCancel: Boolean = true,
): SimpleSQLiteQuery {
    val mainQuery = "SELECT * FROM documents"

    val query = mainQuery.addFilters(
            sqlTableFilters = SqlTableFilters(
                    tableName = "documents",
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