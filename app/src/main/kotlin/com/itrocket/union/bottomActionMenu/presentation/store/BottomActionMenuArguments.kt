package com.itrocket.union.bottomActionMenu.presentation.store

import android.os.Parcelable
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuTab
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BottomActionMenuArguments (val selectedAction: BottomActionMenuTab): Parcelable