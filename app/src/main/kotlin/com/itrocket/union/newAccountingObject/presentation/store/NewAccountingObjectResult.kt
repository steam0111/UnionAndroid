package com.itrocket.union.newAccountingObject.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewAccountingObjectResult(val accountingObject: AccountingObjectDomain) : Parcelable