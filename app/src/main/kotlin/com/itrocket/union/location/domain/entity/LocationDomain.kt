package com.itrocket.union.location.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationDomain(
    val id: String,
    val value: String,
    val type: String,
    val locationTypeId: String,
) : Parcelable