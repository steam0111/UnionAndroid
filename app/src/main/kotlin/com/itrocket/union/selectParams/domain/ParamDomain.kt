package com.itrocket.union.selectParams.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParamDomain(val title: String, val value: String) : Parcelable