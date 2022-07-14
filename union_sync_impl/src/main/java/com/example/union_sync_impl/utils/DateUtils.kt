package com.example.union_sync_impl.utils

import java.text.SimpleDateFormat
import java.util.*

private const val SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SS"

fun getMillisDateFromServerFormat(date: String?): Long? {
    val formatter = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())
    return try {
        formatter.parse(date)?.time ?: 0
    } catch (t: Throwable) {
        null
    }
}

fun getServerFormatFromMillis(millis: Long?): String? {
    val date = getDateFromMillis(millis)
    return try {
        SimpleDateFormat(SERVER_FORMAT, Locale.getDefault()).format(date)
    } catch (t: Throwable) {
        ""
    }
}

fun getDateFromMillis(dateMillis: Long?): Date? {
    val formatter = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())
    val date = getStringDateFromMillis(dateMillis)
    return try {
        formatter.parse(date)
    } catch (t: Throwable) {
        null
    }
}

fun getStringDateFromMillis(millis: Long?): String? {
    val date = Date(millis ?: return null)
    val formatter = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())
    return try {
        formatter.format(date)
    } catch (t: Throwable) {
        ""
    }
}