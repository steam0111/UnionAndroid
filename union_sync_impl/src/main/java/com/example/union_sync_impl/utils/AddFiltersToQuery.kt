package com.example.union_sync_impl.utils

fun String.addFilters(
    sqlTableFilters: SqlTableFilters
): String {
    val filters = mutableListOf<String>()
    sqlTableFilters.filter.forEach { sqlFilter ->
        when (sqlFilter) {
            is SqlFilter.Field -> {
                filters.add("${sqlTableFilters.tableName}.${sqlFilter.name} = \'${sqlFilter.value}\'")
            }
            is SqlFilter.Contains -> {
                filters.add("${sqlTableFilters.tableName}.${sqlFilter.name} LIKE \'%${sqlFilter.value}%\'")
            }
            is SqlFilter.ContainsInList -> {
                val searchInFieldsFilter = sqlFilter.names.joinToString(" OR ") { name ->
                    "${sqlTableFilters.tableName}.${name} LIKE \'%${sqlFilter.value}%\'"
                }
                if (searchInFieldsFilter.isNotEmpty()) {
                    filters.add("($searchInFieldsFilter)")
                }
            }
            is SqlFilter.Fields -> {
                val values = sqlFilter.values.map { "\'$it\'" }
                filters.add("${sqlTableFilters.tableName}.${sqlFilter.name} IN (${values.joinToString(",")})")
            }
        }
    }

    val filterExpression = if (filters.isNotEmpty()) {
        " WHERE ${filters.joinToString(separator = " AND ")}"
    } else {
        ""
    }

    return this +
            filterExpression
}

data class SqlTableFilters(
    val tableName: String,
    val filter: List<SqlFilter>
)

sealed class SqlFilter {
    data class Field(val name: String, val value: String) : SqlFilter()
    data class Fields(val name: String, val values: List<String>) : SqlFilter()
    data class Contains(val name: String, val value: String) : SqlFilter()
    data class ContainsInList(val names: List<String>, val value: String) : SqlFilter()
}

infix fun String.isEquals(value: String): SqlFilter = SqlFilter.Field(this, value)
infix fun String.isEquals(values: List<String>): SqlFilter = SqlFilter.Fields(this, values)
infix fun String.contains(value: String): SqlFilter = SqlFilter.Contains(this, value)
infix fun List<String>.contains(value: String): SqlFilter = SqlFilter.ContainsInList(this, value)

