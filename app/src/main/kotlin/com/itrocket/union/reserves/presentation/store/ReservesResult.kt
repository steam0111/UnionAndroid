package com.itrocket.union.reserves.presentation.store

import android.os.Parcelable
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservesResult(
    val reserve: ReservesDomain
) : Parcelable