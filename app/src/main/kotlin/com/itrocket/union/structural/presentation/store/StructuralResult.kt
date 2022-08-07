package com.itrocket.union.structural.presentation.store

import android.os.Parcelable
import com.itrocket.union.manual.StructuralParamDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class StructuralResult(val structural: StructuralParamDomain) : Parcelable