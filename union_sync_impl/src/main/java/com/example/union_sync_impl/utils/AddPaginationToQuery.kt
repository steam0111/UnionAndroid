package com.example.union_sync_impl.utils

fun String.addPagination(limit: Long? = null, offset: Long? = null): String {
    return this + if (limit != null && offset != null) " LIMIT $limit OFFSET $offset" else ""
}