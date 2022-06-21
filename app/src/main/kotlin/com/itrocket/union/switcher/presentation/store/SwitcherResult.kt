package com.itrocket.union.switcher.presentation.store

import android.os.Parcelable
import com.itrocket.union.switcher.domain.entity.SwitcherDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class SwitcherResult(val result: SwitcherDomain) : Parcelable