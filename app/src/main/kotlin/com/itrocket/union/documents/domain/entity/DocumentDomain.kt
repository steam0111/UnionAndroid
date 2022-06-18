package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentDomain(
    val number: String,
    val time: String,
    val documentStatus: DocumentStatus,
    val status: ObjectStatus,
    val objectType: ObjectType,
    val documentType: DocumentTypeDomain,
    val params: List<ParamDomain>,
    val date: String,
    val accountingObjects: List<AccountingObjectDomain> = listOf(),
    val reserves: List<ReservesDomain> = listOf()
) : Parcelable