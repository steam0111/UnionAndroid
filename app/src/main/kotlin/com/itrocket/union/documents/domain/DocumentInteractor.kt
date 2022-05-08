package com.itrocket.union.documents.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.domain.entity.DocumentDateType
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.presentation.view.DocumentView
import com.itrocket.union.documents.presentation.view.toDocumentItemView
import com.itrocket.union.utils.getTextDateFromStringDate
import com.itrocket.union.utils.isCurrentYear
import com.itrocket.union.utils.isToday
import com.itrocket.union.utils.isYesterday
import com.itrocket.utils.resolveItem

class DocumentInteractor(
    private val repository: DocumentRepository,
    private val coreDispatchers: CoreDispatchers
) {

    private fun groupDocuments(documents: List<DocumentDomain>): List<DocumentView> {
        val documentViews = arrayListOf<DocumentView>()
        documents.groupBy { it.date }.forEach {
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
                it.toDocumentItemView()
            })
        }
        return documentViews
    }

    fun resolveRotatedDates(rotatedDates: List<String>, newDate: String): List<String> {
        return rotatedDates.toMutableList().resolveItem(newDate)
    }

    suspend fun getDocuments(): List<DocumentView> {
        return withContext(coreDispatchers.io) {
            groupDocuments(
                repository.getDocuments()
            )
        }
    }

}