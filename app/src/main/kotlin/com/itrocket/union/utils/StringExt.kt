package com.itrocket.union.utils

import androidx.compose.runtime.Composable

fun String?.ifBlankOrNull(block: () -> String): String {
    return if (this.isNullOrBlank()) {
        block()
    } else {
        this
    }
}

@Composable
fun String?.ifBlankOrNullComposable(block: @Composable () -> String): String {
    return if (this.isNullOrBlank()) {
        block()
    } else {
        this
    }
}