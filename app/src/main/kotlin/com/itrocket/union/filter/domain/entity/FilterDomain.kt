package com.itrocket.union.filter.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterDomain(
    val name: String,
    val values: List<String> = listOf(),
    val valueList: List<String> = listOf(),
    val filterValueType: FilterValueType
) : Parcelable

enum class FilterValueType {
    INPUT, MULTI_SELECT_LIST, SINGLE_SELECT_LIST, DATE, LOCATION
}