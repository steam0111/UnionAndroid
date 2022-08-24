package com.itrocket.union.selectParams.presentation.store

import android.os.Parcelable
import com.itrocket.union.manual.ParamDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectParamsArguments(val params: List<ParamDomain>, val currentStep: Int, val allParams: List<ParamDomain>) : Parcelable