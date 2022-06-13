package com.itrocket.union.selectParams.presentation.store

import android.os.Parcelable
import com.itrocket.union.manual.ParamDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectParamsArguments(val params: List<ParamDomain>, val currentStep: Int) : Parcelable