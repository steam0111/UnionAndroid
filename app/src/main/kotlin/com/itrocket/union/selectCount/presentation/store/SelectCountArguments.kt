package com.itrocket.union.selectCount.presentation.store

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectCountArguments(val id: String, val count: Long) : Parcelable