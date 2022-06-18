package com.itrocket.union.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

private const val YEAR = "yyyy"
private const val DATE_FORMAT = "dd.MM.yyyy"
private const val UI_DATE_FORMAT = "dd MMMM"
private const val TIME_FORMAT ="HH:mm"

fun getStringDateFromMillis(millis: Long): String {
    val date = Date(millis)
    val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return try {
        formatter.format(date)
    } catch (t: Throwable) {
        ""
    }
}

fun getTimeFromMillis(millis: Long): String {
    val date = Date(millis)
    val formatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
    return try {
        formatter.format(date)
    } catch (t: Throwable) {
        ""
    }
}

fun getDateFromString(date: String): Date? {
    val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return try {
        formatter.parse(date)
    } catch (t: Throwable) {
        null
    }
}

fun isToday(dateStr: String): Boolean {
    val date = getDateFromString(dateStr)
    return if (date != null) {
        DateUtils.isToday(date.time)
    } else {
        false
    }
}

fun isCurrentYear(dateStr: String): Boolean {
    val date = getDateFromString(dateStr)
    return if (date != null) {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val dateYear = SimpleDateFormat(YEAR, Locale.getDefault()).format(date).toIntOrNull() ?: 0
        dateYear == currentYear
    } else {
        false
    }
}

fun getTextDateFromStringDate(dateStr: String): String {
    val date = getDateFromString(dateStr)
    return try {
        SimpleDateFormat(UI_DATE_FORMAT, Locale.getDefault()).format(date ?: return dateStr)
    } catch (t: Throwable) {
        dateStr
    }
}

fun isYesterday(dateStr: String): Boolean {
    val date = getDateFromString(dateStr)
    return if (date != null) {
        DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS)
    } else {
        false
    }
}