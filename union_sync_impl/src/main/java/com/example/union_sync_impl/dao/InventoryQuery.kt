package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.*

fun sqlInventoryQuery(
    textQuery: String? = null,
    structuralId: String? = null,
    molId: String? = null,
    updateDate: Long? = null,
    limit: Long? = null,
    offset: Long? = null,
    isFilterCount: Boolean = false
): SimpleSQLiteQuery {
    val mainQuery = if (isFilterCount) {
        "SELECT COUNT(*) FROM inventories"
    } else {
        "SELECT inventories.*," +
                "" +
                "structural.id AS structural_id, " +
                "structural.catalogItemName AS structural_catalogItemName, " +
                "structural.name AS structural_name, " +
                "structural.parentId AS structural_parentId, " +
                "" +
                "employees.id AS employees_id, " +
                "employees.catalogItemName AS employees_catalogItemName, " +
                "employees.firstname AS employees_firstname, " +
                "employees.lastname AS employees_lastname, " +
                "employees.patronymic AS employees_patronymic, " +
                "employees.number AS employees_number, " +
                "employees.nfc AS employees_nfc " +
                "" +
                "FROM inventories " +
                "LEFT JOIN employees ON inventories.employeeId = employees.id " +
                "LEFT JOIN structural ON inventories.structuralId = structural.id "
    }

    return SimpleSQLiteQuery(
        mainQuery.getInventoriesFilterPartQuery(
            textQuery = textQuery,
            structuralId = structuralId,
            molId = molId,
            updateDate = updateDate,
            limit = limit,
            offset = offset
        )
    )
}

private fun sqlInventoryCountQuery(
    textQuery: String? = null,
    structuralId: String? = null,
    molId: String? = null,
    updateDate: Long? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT COUNT(*) FROM inventories "

    return SimpleSQLiteQuery(
        mainQuery.getInventoriesFilterPartQuery(
            textQuery = textQuery,
            structuralId = structuralId,
            molId = molId,
            updateDate = updateDate
        )
    )
}

fun String.getInventoriesFilterPartQuery(
    textQuery: String? = null,
    structuralId: String? = null,
    molId: String? = null,
    updateDate: Long? = null,
    limit: Long? = null,
    offset: Long? = null,
): String =
    addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "inventories",
            filter = buildList {
                textQuery?.let {
                    add("id" contains textQuery)
                }
                structuralId?.let {
                    add("structuralId" isEquals structuralId)
                }
                molId?.let {
                    add("employeeId" isEquals molId)
                }
                updateDate?.let {
                    add("updateDate" more updateDate)
                }
            }
        )
    ).addOrder("updateDate").addPagination(
        limit,
        offset
    )

