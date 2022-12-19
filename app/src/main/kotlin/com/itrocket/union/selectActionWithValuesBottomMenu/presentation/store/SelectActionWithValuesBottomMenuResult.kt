package com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documents.domain.entity.ActionsWithIdentifyObjects
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectActionWithValuesBottomMenuResult(
    val actionType: ActionsWithIdentifyObjects,
    val accountingObject: AccountingObjectDomain,
) : Parcelable
