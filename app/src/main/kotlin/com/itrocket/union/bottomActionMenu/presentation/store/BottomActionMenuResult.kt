package com.itrocket.union.bottomActionMenu.presentation.store

import android.os.Parcelable
import com.itrocket.union.documents.domain.entity.ObjectAction
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class BottomActionMenuResult(val type: ObjectAction) : Parcelable
