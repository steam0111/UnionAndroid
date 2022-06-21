package com.itrocket.union.switcher.domain.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.switcher.presentation.store.SwitcherItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class SwitcherDomain(
    @StringRes val titleId: Int,
    val values: List<SwitcherItem>,
    val currentValue: SwitcherItem,
    val entityId: String
) :
    Parcelable