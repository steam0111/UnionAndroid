package com.itrocket.union.filter.presentation.store

import android.os.Parcelable
import com.itrocket.union.filter.domain.entity.FilterDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterArguments(val argument: List<FilterDomain>): Parcelable