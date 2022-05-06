package com.itrocket.union.accountingObjectDetail.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountingObjectDetailArguments(
    val argument: AccountingObjectDomain
) : Parcelable