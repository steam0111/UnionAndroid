package com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectActionWithValuesBottomMenuArguments(
    val accountingObjectDomain: AccountingObjectDomain,
    val accountingObjects: List<AccountingObjectDomain>
) : Parcelable