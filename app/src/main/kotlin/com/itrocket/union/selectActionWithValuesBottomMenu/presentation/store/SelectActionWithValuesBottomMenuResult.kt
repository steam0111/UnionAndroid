package com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectActionWithValuesBottomMenuResult(
    val accountingObjects: List<AccountingObjectDomain>
) : Parcelable
