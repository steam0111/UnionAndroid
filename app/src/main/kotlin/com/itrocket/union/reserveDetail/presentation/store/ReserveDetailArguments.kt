package com.itrocket.union.reserveDetail.presentation.store

import android.os.Parcelable
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReserveDetailArguments(val argument: ReservesDomain) : Parcelable