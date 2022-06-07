package com.itrocket.union.selectParams.presentation.store

import android.os.Parcelable
import com.itrocket.union.selectParams.domain.ParamDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectParamsResult(val params: List<ParamDomain>) : Parcelable