package com.itrocket.union.documents.domain

import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.documents.domain.entity.DocumentDateType
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.documents.presentation.view.DocumentView
import com.itrocket.union.documents.presentation.view.toDocumentItemView
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
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
    suspend fun getDocuments(
        searchQuery: String = "",
        params: List<ParamDomain>?,
        type: DocumentTypeDomain
    ): Flow<List<DocumentView>> {
        return withContext(coreDispatchers.io) {
            groupDocuments(
                if (type == DocumentTypeDomain.ALL) {
                    repository.getAllDocuments(searchQuery, params)
                } else {
                    repository.getDocuments(type, searchQuery)
                }
            )
        }
    }

    suspend fun createDocument(
        type: DocumentTypeDomain,
        listType: ObjectType
    ): DocumentDomain {
        return withContext(coreDispatchers.io) {
            val exploitingId: String?
            val locationIds: List<String>?

            when (listType) {
                ObjectType.MAIN_ASSETS -> {
                    exploitingId = getExploitingByManualTypes(type.accountingObjectManualTypes)
                    locationIds = getLocationsByManualTypes(type.accountingObjectManualTypes)
                }
                ObjectType.RESERVES -> {
                    exploitingId = getExploitingByManualTypes(type.reserveManualTypes)
                    locationIds = getLocationsByManualTypes(type.reserveManualTypes)
                }
            }

            val documentId = repository.createDocument(
                DocumentCreateSyncEntity(
                    organizationId = "",
                    exploitingId = exploitingId,
                    molId = "",
                    documentType = type.name,
                    accountingObjectsIds = listOf(),
                    locationIds = locationIds,
                    creationDate = System.currentTimeMillis(),
                    documentStatus = DocumentStatus.CREATED.name,
                    documentStatusId = DocumentStatus.CREATED.name,
                    reservesIds = listOf(),
                    objectType = listType.name
                )
            )
            repository.getDocumentById(documentId)
        }
    }

    private fun getExploitingByManualTypes(manualTypes: List<ManualType>): String? {
        return if (manualTypes.contains(ManualType.EXPLOITING)) {
            ""
        } else {
            null
        }
    }

    private fun getLocationsByManualTypes(manualTypes: List<ManualType>): List<String>? {
        return if (manualTypes.contains(ManualType.LOCATION)) {
            listOf()
        } else {
            null
        }
    }

    private suspend fun groupDocuments(documents: Flow<List<DocumentDomain>>): Flow<List<DocumentView>> {
        return withContext(coreDispatchers.io) {
            documents.map {
                val documentViews = arrayListOf<DocumentView>()
                it.groupBy {
                    getStringDateFromMillis(it.creationDate)
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
                        it.toDocumentItemView(getStringDateFromMillis(it.creationDate))
                    })
                }
                documentViews
            }
        }
    }

    fun resolveRotatedDates(rotatedDates: List<String>, newDate: String): List<String> {
        return rotatedDates.toMutableList().resolveItem(newDate)
    }

    fun getFilters(): List<ParamDomain> {
        return listOf(
            ParamDomain(
                type = ManualType.ORGANIZATION,
                value = ""
            ),
            ParamDomain(
                type = ManualType.MOL,
                value = ""
            ),
            ParamDomain(
                type = ManualType.EXPLOITING,
                value = ""
            )
        )
    }
}