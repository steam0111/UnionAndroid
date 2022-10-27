package com.example.union_sync_impl.utils

private const val DATABASE_TRUE = "1"

fun MutableList<SqlFilter>.addNonCancelFilter() = add("cancel" isNotEquals DATABASE_TRUE)
