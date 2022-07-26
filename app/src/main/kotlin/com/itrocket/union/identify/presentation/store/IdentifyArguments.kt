package com.itrocket.union.identify.presentation.store

import android.os.Parcelable
import com.itrocket.union.identify.domain.entity.IdentifyDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class IdentifyArguments(val document: IdentifyDomain) : Parcelable