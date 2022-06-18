package com.itrocket.union.documents.presentation.view

import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.documents.domain.entity.DocumentDateType
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.manual.ParamDomain

sealed class DocumentView {
    data class DocumentItemView(
        val number: String,
        val time: String,
        val documentStatus: DocumentStatus,
        val objectType: ObjectType,
        val objectStatus: ObjectStatus,
        val documentType: DocumentTypeDomain,
        val params: List<ParamDomain>,
        val date: String
    ) : DocumentView()

    data class DocumentDateView(
        val date: String,
        val dayType: DocumentDateType,
        val dateUi: String
    ) :
        DocumentView()
}

fun DocumentView.DocumentItemView.toDocumentDomain() = DocumentDomain(
    number = number,
    time = time,
    documentStatus = documentStatus,
    status = objectStatus,
    objectType = objectType,
    date = date,
    documentType = documentType,
    params = params
)

fun DocumentDomain.toDocumentItemView() = DocumentView.DocumentItemView(
    number = number,
    time = time,
    documentStatus = documentStatus,
    objectStatus = status,
    objectType = objectType,
    date = date,
    documentType = documentType,
    params = params
)