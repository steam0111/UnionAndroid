package com.itrocket.union.bottomActionMenu.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class BottomActionMenuResult(val listAO: List<AccountingObjectDomain> = listOf()) : Parcelable
