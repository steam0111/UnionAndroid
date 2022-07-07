package com.example.union_sync_impl.utils

import android.util.Log
import org.junit.Test

class AddFiltersToQueryTest {

    @Test
    fun rightQuery_onEmptyFilter() {
        val expect = "SELECT accounting_objects.*," +
                "FROM accounting_objects"

        val mainQuery = "SELECT accounting_objects.*," +
                "FROM accounting_objects"

        val result = mainQuery.addFilters(
            sqlTableFilters = SqlTableFilters(
                "",
                listOf()
            )
        )

        assert(expect == result)
    }

    @Test
    fun rightQuery_onNotEmptyFilter() {
        val mainQuery = "SELECT * " +
                "FROM accounting_objects"

        val expect = mainQuery +
                " WHERE accounting_objects.organizationId = '197-197-197' " +
                "AND accounting_objects.employeeId = '197-197-197' " +
                "AND accounting_objects.someId = '197-197-197' " +
                "AND accounting_objects.castId IN ('197-197-197','197-197-197') " +
                "AND accounting_objects.name LIKE '%azazin%'"

        val result = mainQuery.addFilters(
            sqlTableFilters = SqlTableFilters(
                "accounting_objects",
                listOf(
                    "organizationId" isEquals "197-197-197",
                    "employeeId" isEquals "197-197-197",
                    "someId" isEquals "197-197-197",
                    "castId" isEquals listOf("197-197-197", "197-197-197"),
                    "name" contains "azazin"
                )
            )
        )

        assert(expect == result)
    }

    @Test
    fun rightQuery_onContain() {
        val mainQuery = "SELECT * FROM accounting_objects"

        val expect =
            mainQuery + " WHERE accounting_objects.organizationId = '396397f6-0f00-47de-8d40-29db735673f2' " +
                    "AND accounting_objects.name LIKE '%ic%'"

        val result = mainQuery.addFilters(
            sqlTableFilters = SqlTableFilters(
                "accounting_objects",
                listOf(
                    "organizationId" isEquals "396397f6-0f00-47de-8d40-29db735673f2",
                    "name" contains "ic"
                )
            )
        )

        assert(expect == result)
    }

    @Test
    fun rightQuery_onContainInFewFields() {
        val mainQuery = "SELECT * FROM employees"

        val expect = mainQuery + " WHERE employees.organizationId = '009988098711' AND " +
                "(employees.firstname LIKE '%sss%' OR employees.lastname LIKE '%sss%')"

        val result = mainQuery.addFilters(
            sqlTableFilters = SqlTableFilters(
                "employees",
                listOf(
                    "organizationId" isEquals "009988098711",
                    listOf("firstname", "lastname") contains "sss"
                )
            )
        )

        assert(expect == result)
    }

    @Test
    fun rightQuery_onMoreThatField() {
        val mainQuery = "SELECT * FROM accounting_objects"

        val expect = "$mainQuery WHERE accounting_objects.updateDate > '1657206886505'"

        val result = mainQuery.addFilters(
            sqlTableFilters = SqlTableFilters(
                "accounting_objects",
                listOf(
                    "updateDate" more 1657206886505
                )
            )
        )

        assert(expect == result)
    }
}