package com.itrocket.union.identify.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class IdentifyArguments(
    val accountingObjectDomain: List<AccountingObjectDomain>
) : Parcelable