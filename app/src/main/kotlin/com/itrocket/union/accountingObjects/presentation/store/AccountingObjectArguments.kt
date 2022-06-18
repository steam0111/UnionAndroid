package com.itrocket.union.accountingObjects.presentation.store

import android.os.Parcelable
import com.itrocket.union.manual.ParamDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountingObjectArguments(val params: List<ParamDomain>) : Parcelable