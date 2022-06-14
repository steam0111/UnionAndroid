package com.itrocket.union.manual

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParamValueDomain(val id: String, val value: String): Parcelable