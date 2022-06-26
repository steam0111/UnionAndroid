package com.example.union_sync_impl.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.union_sync_impl.utils.SqlTableFilters
import com.example.union_sync_impl.utils.addFilters
import com.example.union_sync_impl.utils.contains

fun sqlInventoryQuery(
    textQuery: String? = null
): SimpleSQLiteQuery {
    val mainQuery = "SELECT inventories.*," +
            "" +
            "organizations.id AS organizations_id, " +
            "organizations.catalogItemName AS organizations_catalogItemName, " +
            "organizations.name AS organizations_name, " +
            "organizations.actualAddress AS organizations_actualAddress, " +
            "organizations.legalAddress AS organizations_legalAddress, " +
            "" +
            "employees.id AS employees_id, " +
            "employees.catalogItemName AS employees_catalogItemName, " +
            "employees.firstname AS employees_firstname, " +
            "employees.lastname AS employees_lastname, " +
            "employees.patronymic AS employees_patronymic, " +
            "employees.organizationId AS employees_organizationId, " +
            "employees.number AS employees_number, " +
            "employees.nfc AS employees_nfc " +
            "" +
            "FROM inventories " +
            "LEFT JOIN organizations ON inventories.organizationId = organizations.id " +
            "LEFT JOIN employees ON inventories.employeeId = employees.id "

    val query = mainQuery.addFilters(
        sqlTableFilters = SqlTableFilters(
            tableName = "inventories",
            filter = buildList {
                textQuery?.let {
                    add("name" contains textQuery)
                }
            }
        )
    )

    return SimpleSQLiteQuery(query)
}