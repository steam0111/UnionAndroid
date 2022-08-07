package com.itrocket.union.structural.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StructuralDomain(val id: String, val value: String) : Parcelable