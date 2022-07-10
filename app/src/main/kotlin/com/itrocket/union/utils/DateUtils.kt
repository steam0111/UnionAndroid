package com.itrocket.union.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

private const val YEAR = "yyyy"
private const val DATE_FORMAT = "dd.MM.yyyy"
private const val UI_DATE_FORMAT = "dd MMMM"
private const val TIME_FORMAT = "HH:mm"

//Sample: 12 December
fun getTextDateFromMillis(dateMillis: Long): String {
    val date = getDateFromMillis(dateMillis)
    return try {
        SimpleDateFormat(UI_DATE_FORMAT, Locale.getDefault()).format(date)
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

fun getDateFromMillis(dateMillis: Long): Date? {
    val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    val date = getStringDateFromMillis(dateMillis)
    return try {
        formatter.parse(date)
    } catch (t: Throwable) {
        null
    }
}

//Sample: 12.12.12
fun getStringDateFromMillis(millis: Long): String {
    val date = Date(millis)
    val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return try {
        formatter.format(date)
    } catch (t: Throwable) {
        ""
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

fun getTimeFromMillis(millis: Long): String {
    val date = Date(millis)
    val formatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
    return try {
        formatter.format(date)
    } catch (t: Throwable) {
        ""
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

fun isYesterday(dateStr: String): Boolean {
    val date = getDateFromString(dateStr)
    return if (date != null) {
        DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS)
    } else {
        false
    }
}