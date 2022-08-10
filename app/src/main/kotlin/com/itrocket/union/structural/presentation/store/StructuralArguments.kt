package com.itrocket.union.structural.presentation.store

import android.os.Parcelable
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.StructuralParamDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StructuralArguments(
    val structural: StructuralParamDomain = StructuralParamDomain(manualType = ManualType.STRUCTURAL),
    val isCanEdit: Boolean
) :
    Parcelable