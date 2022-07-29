package com.itrocket.union.bottomActionMenu.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BottomActionMenuArguments(
    val accountingObjectDomain: AccountingObjectDomain,
    val listAO: List<AccountingObjectDomain>
) : Parcelable