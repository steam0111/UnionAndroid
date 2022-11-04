package com.itrocket.union.accountingObjectDetail.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountingObjectDetailResult(val accountingObject: AccountingObjectDomain) : Parcelable