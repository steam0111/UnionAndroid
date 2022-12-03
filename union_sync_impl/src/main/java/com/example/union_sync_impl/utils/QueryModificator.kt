package com.example.union_sync_impl.utils

fun String.addPagination(limit: Long? = null, offset: Long? = null): String {
    return this + if (limit != null && offset != null) " LIMIT $limit OFFSET $offset" else ""
}

fun String.addOrder(fieldName: String? = null, order: Order): String {
    return this + if (fieldName != null) " ORDER BY $fieldName ${order.name}" else ""
}

enum class Order {
    ASC, DESC
}