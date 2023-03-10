package com.itrocket.union.selectParams.presentation.store

import android.os.Parcelable
import com.itrocket.union.manual.ParamDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectParamsResult(val params: List<ParamDomain>) : Parcelable