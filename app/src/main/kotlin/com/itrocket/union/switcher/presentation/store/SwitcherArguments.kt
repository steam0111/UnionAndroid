package com.itrocket.union.switcher.presentation.store

import android.os.Parcelable
import com.itrocket.union.switcher.domain.entity.SwitcherDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SwitcherArguments(val argument: SwitcherDomain) : Parcelable