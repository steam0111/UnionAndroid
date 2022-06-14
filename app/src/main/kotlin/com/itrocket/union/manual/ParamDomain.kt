package com.itrocket.union.manual

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParamDomain(val paramValue: ParamValueDomain?, val type: ManualType) : Parcelable