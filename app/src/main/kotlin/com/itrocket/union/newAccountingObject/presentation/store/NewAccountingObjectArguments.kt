package com.itrocket.union.newAccountingObject.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewAccountingObjectArguments(val accountingObject: AccountingObjectDomain) : Parcelable