package com.itrocket.union.selectCount.presentation.store

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class SelectCountArguments(val id: String, val count: BigDecimal) : Parcelable