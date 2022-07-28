package com.itrocket.union.bottomActionMenu.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuTab
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BottomActionMenuArguments(
    val bottomActionMenuDocument: AccountingObjectDomain
) : Parcelable