package com.itrocket.union.documents.presentation.view

import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.documents.domain.entity.DocumentDateType
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.ObjectType

sealed class DocumentView {
    data class DocumentItemView(
        val number: String,
        val time: String,
        val documentStatus: DocumentStatus,
        val objectType: ObjectType,
        val objectStatus: ObjectStatus,
        val documentInfo: List<String>,
        val date: String
    ) : DocumentView()

    data class DocumentDateView(
        val date: String,
        val dayType: DocumentDateType,
        val dateUi: String
    ) :
        DocumentView()
}

fun DocumentDomain.toDocumentItemView() = DocumentView.DocumentItemView(
    number = number,
    time = time,
    documentStatus = documentStatus,
    objectStatus = status,
    objectType = objectType,
    documentInfo = documentInfo,
    date = date
)