package com.itrocket.union.documents.presentation.view

import com.itrocket.union.documents.domain.entity.DocumentDateType
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.utils.getStringDateFromMillis
import com.itrocket.union.utils.getTimeFromMillis

sealed class DocumentView {
    data class DocumentItemView(
        val id: String?,
        val number: String,
        val documentStatus: DocumentStatus,
        val documentType: DocumentTypeDomain,
        val params: List<ParamDomain>,
        val date: Long,
        val dateUi: String,
        val userInserted: String?
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
    id = id,
    number = number,
    documentStatus = documentStatus,
    creationDate = date,
    documentType = documentType,
    params = params,
    documentStatusId = documentStatus.name,
    userInserted = userInserted,
    userUpdated = ""
)

fun DocumentDomain.toDocumentItemView(dateUi: String): DocumentView.DocumentItemView {
    val filteredParams = params.filter {
        it.type != ManualType.BALANCE_UNIT_FROM && it.type != ManualType.BALANCE_UNIT_TO && it.type != ManualType.BALANCE_UNIT
    }

    return DocumentView.DocumentItemView(
        id = id,
        number = number.orEmpty(),
        documentStatus = documentStatus,
        date = creationDate ?: 0,
        documentType = documentType,
        params = filteredParams,
        dateUi = dateUi,
        userInserted = userInserted
    )
}