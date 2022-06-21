package com.itrocket.union.documents.domain

import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.documents.domain.entity.DocumentDateType
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.presentation.view.DocumentView
import com.itrocket.union.documents.presentation.view.toDocumentItemView
import com.itrocket.union.manual.ManualType
import com.itrocket.union.utils.getStringDateFromMillis
import com.itrocket.union.utils.getTextDateFromMillis
import com.itrocket.union.utils.getTextDateFromStringDate
import com.itrocket.union.utils.isCurrentYear
import com.itrocket.union.utils.isToday
import com.itrocket.union.utils.isYesterday
import com.itrocket.utils.resolveItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DocumentInteractor(
    private val repository: DocumentRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getDocuments(type: DocumentTypeDomain): Flow<List<DocumentView>> {
        return withContext(coreDispatchers.io) {
            groupDocuments(
                if (type == DocumentTypeDomain.ALL) {
                    repository.getAllDocuments()
                } else {
                    repository.getDocuments(type)
                }
            )
        }
    }

    suspend fun createDocument(
        type: DocumentTypeDomain
    ): DocumentDomain {
        return withContext(coreDispatchers.io) {
            val exploitingId = if (type.manualType == ManualType.EXPLOITING) {
                ""
            } else {
                null
            }
            val locationIds = if (type.manualType == ManualType.LOCATION) {
                listOf<String>()
            } else {
                null
            }
            val documentId = repository.createDocument(
                DocumentCreateSyncEntity(
                    organizationId = "",
                    exploitingId = exploitingId,
                    molId = "",
                    documentType = type.name,
                    accountingObjectsIds = listOf(),
                    locationIds = locationIds
                )
            )
            repository.getDocumentById(documentId)
        }
    }

    private suspend fun groupDocuments(documents: Flow<List<DocumentDomain>>): Flow<List<DocumentView>> {
        return withContext(coreDispatchers.io) {
            documents.map {
                val documentViews = arrayListOf<DocumentView>()
                it.groupBy {
                    getStringDateFromMillis(it.date)
                }.forEach {
                    val documentDateType = when {
                        isToday(it.key) -> DocumentDateType.TODAY
                        isYesterday(it.key) -> DocumentDateType.YESTERDAY
                        else -> DocumentDateType.OTHER
                    }
                    documentViews.add(
                        DocumentView.DocumentDateView(
                            dateUi = if (isCurrentYear(it.key)) {
                                getTextDateFromStringDate(it.key)
                            } else {
                                it.key
                            },
                            dayType = documentDateType,
                            date = it.key
                        )
                    )
                    documentViews.addAll(it.value.map {
                        it.toDocumentItemView(getStringDateFromMillis(it.date))
                    })
                }
                documentViews
            }
        }
    }

    fun resolveRotatedDates(rotatedDates: List<String>, newDate: String): List<String> {
        return rotatedDates.toMutableList().resolveItem(newDate)
    }
}