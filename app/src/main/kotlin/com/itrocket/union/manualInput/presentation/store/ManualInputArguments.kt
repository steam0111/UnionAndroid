package com.itrocket.union.manualInput.presentation.store

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ManualInputArguments(val manualType: ManualInputType) : Parcelable