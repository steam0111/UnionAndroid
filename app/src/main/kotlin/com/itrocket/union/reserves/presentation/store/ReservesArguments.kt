package com.itrocket.union.reserves.presentation.store

import android.os.Parcelable
import com.itrocket.union.manual.ParamDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservesArguments(
    val params: List<ParamDomain>,
    val selectedReservesIds: List<String>,
    val isFromDocument: Boolean
) : Parcelable