package com.itrocket.union.documents.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateArguments
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.presentation.view.DocumentComposeFragmentDirections
import com.itrocket.union.documents.presentation.view.DocumentView
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.transit.presentation.store.TransitArguments

interface DocumentStore : Store<DocumentStore.Intent, DocumentStore.State, DocumentStore.Label> {

    sealed class Intent {
        class OnFilterResult(val params: List<ParamDomain>) : Intent()
        object OnArrowBackClicked : Intent()
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnDocumentCreateClicked : Intent()
        data class OnDocumentClicked(val documentView: DocumentView.DocumentItemView) : Intent()
        data class OnDateArrowClicked(val date: String) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
    }

    data class State(
        val isListLoading: Boolean = false,
        val isDocumentCreateLoading: Boolean = false,
        val type: DocumentTypeDomain,
        val documents: List<DocumentView> = listOf(),
        val rotatedDates: List<String> = listOf(),
        val isShowSearch: Boolean = false,
        val searchText: String = "",
        val params: List<ParamDomain>? = null,
        val canCreateDocument: Boolean = true
    )

    sealed class Label {
        data class ShowDocumentCreate(
            private val document: DocumentDomain
        ) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentComposeFragmentDirections.toDocumentCreate(
                    DocumentCreateArguments(
                        document = document
                    )
                )

        }

        data class ShowTransitCreate(
            private val transit: DocumentDomain
        ) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentComposeFragmentDirections.toTransitCreate(
                    TransitArguments(
                        transit = transit
                    )
                )

        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        object GoBack : Label(), GoBackNavigationLabel
        data class ShowFilter(
            val filters: List<ParamDomain>,
            val documentTypeDomain: DocumentTypeDomain
        ) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentComposeFragmentDirections.toFilter(
                    FilterArguments(
                        filters,
                        CatalogType.Documents(documentTypeDomain)
                    )
                )
        }
    }
}