package com.itrocket.union.documents.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus

data class DocumentDomain(
    val number: String,
    val time: String,
    val documentStatus: DocumentStatus,
    val status: ObjectStatus,
    val objectType: ObjectType,
    val date: String,
    val documentInfo: List<String>
)