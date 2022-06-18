package com.itrocket.union.accountingObjects.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountingObjectResult(val accountingObject: AccountingObjectDomain) : Parcelable