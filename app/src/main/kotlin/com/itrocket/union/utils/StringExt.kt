package com.itrocket.union.utils

fun String?.ifBlankOrNull(block: () -> String): String {
    return if (this.isNullOrBlank()) {
        block()
    } else {
        this
    }
}