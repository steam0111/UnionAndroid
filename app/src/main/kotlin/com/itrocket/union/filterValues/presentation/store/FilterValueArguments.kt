package com.itrocket.union.filterValues.presentation.store

import android.os.Parcelable
import com.itrocket.union.filter.domain.entity.FilterDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterValueArguments(val argument: FilterDomain): Parcelable