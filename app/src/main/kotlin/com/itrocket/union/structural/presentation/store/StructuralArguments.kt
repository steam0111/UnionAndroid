package com.itrocket.union.structural.presentation.store

import android.os.Parcelable
import com.itrocket.union.manual.StructuralParamDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StructuralArguments(val structural: StructuralParamDomain) : Parcelable