package com.itrocket.union.documents.presentation.view

import com.itrocket.union.documents.domain.entity.DocumentDateType
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.utils.getStringDateFromMillis
import com.itrocket.union.utils.getTimeFromMillis

sealed class DocumentView {
    data class DocumentItemView(
        val number: String,
        val documentStatus: DocumentStatus,
        val documentType: DocumentTypeDomain,
        val params: List<ParamDomain>,
        val date: Long,
        val dateUi: String,
    ) : DocumentView() {
        fun getTextTime() = getTimeFromMillis(date)
    }

    data class DocumentDateView(
        val date: String,
        val dayType: DocumentDateType,
        val dateUi: String
    ) : DocumentView()
}

fun DocumentView.DocumentItemView.toDocumentDomain() = DocumentDomain(
    number = number,
    documentStatus = documentStatus,
    creationDate = date,
    documentType = documentType,
    params = params,
    documentStatusId = documentStatus.name
)

fun DocumentDomain.toDocumentItemView(dateUi: String) = DocumentView.DocumentItemView(
    number = number.orEmpty(),
    documentStatus = documentStatus,
    date = creationDate,
    documentType = documentType,
    params = params,
    dateUi = dateUi
)