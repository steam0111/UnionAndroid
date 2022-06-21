package com.itrocket.union.location.presentation.store

import android.os.Parcelable
import com.itrocket.union.manual.LocationParamDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationArguments(val location: LocationParamDomain): Parcelable