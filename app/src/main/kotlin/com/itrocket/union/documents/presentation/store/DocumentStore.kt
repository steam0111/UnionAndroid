package com.itrocket.union.documents.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.presentation.view.DocumentView

interface DocumentStore : Store<DocumentStore.Intent, DocumentStore.State, DocumentStore.Label> {

    sealed class Intent {
        object OnArrowBackClicked : Intent()
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnDocumentCreateClicked : Intent()
        data class OnDocumentClicked(val id: String) : Intent()
        data class OnDateArrowClicked(val date: String) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val type: DocumentTypeDomain,
        val documents: List<DocumentView> = listOf(),
        val rotatedDates: List<String> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
    }
}