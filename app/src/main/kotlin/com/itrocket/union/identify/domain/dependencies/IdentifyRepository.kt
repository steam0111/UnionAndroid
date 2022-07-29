package com.itrocket.union.identify.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.identify.domain.entity.IdentifyDomain

interface IdentifyRepository {
    suspend fun getIdentify(): List<AccountingObjectDomain>
    suspend fun getDocumentById(id: String): IdentifyDomain

}