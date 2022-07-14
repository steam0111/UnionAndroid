package com.itrocket.union.documents.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.domain.DocumentInteractor
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.documents.presentation.view.DocumentView
import com.itrocket.union.documents.presentation.view.toDocumentDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class DocumentStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val documentInteractor: DocumentInteractor,
    private val arguments: DocumentArguments,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager
) {
    fun create(): DocumentStore =
        object : DocumentStore,
            Store<DocumentStore.Intent, DocumentStore.State, DocumentStore.Label> by storeFactory.create(
                name = "DocumentStore",
                initialState = DocumentStore.State(type = arguments.type),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<DocumentStore.Intent, Unit, DocumentStore.State, Result, DocumentStore.Label> =
        DocumentExecutor()

    private inner class DocumentExecutor :
        BaseExecutor<DocumentStore.Intent, Unit, DocumentStore.State, Result, DocumentStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> DocumentStore.State
        ) {
            searchManager.listenSearch {
                listenDocuments(
                    typeDomain = getState().type,
                    searchText = it,
                    params = getState().params
                )
            }
        }

        override suspend fun executeIntent(
            intent: DocumentStore.Intent,
            getState: () -> DocumentStore.State
        ) {
            when (intent) {
                DocumentStore.Intent.OnArrowBackClicked -> onBackClicked(
                    getState().isShowSearch
                )
                DocumentStore.Intent.OnFilterClicked -> {
                    publish(
                        DocumentStore.Label.ShowFilter(
                            getState().params ?: documentInteractor.getFilters()
                        )
                    )
                }
                is DocumentStore.Intent.OnFilterResult -> {
                    dispatch(Result.FilterParams(intent.params))
                    listenDocuments(
                        params = getState().params,
                        searchText = "",
                        typeDomain = getState().type
                    )
                }
                DocumentStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is DocumentStore.Intent.OnDocumentClicked -> {
                    showDocument(intent.documentView.toDocumentDomain())
                }
                DocumentStore.Intent.OnDocumentCreateClicked -> createDocument(
                    documentType = getState().type
                )
                is DocumentStore.Intent.OnDateArrowClicked -> {
                    val newRotatedDates =
                        documentInteractor.resolveRotatedDates(getState().rotatedDates, intent.date)
                    dispatch(Result.RotatedDate(newRotatedDates))
                }
                is DocumentStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
            }

        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.emit("")
            } else {
                publish(DocumentStore.Label.GoBack)
            }
        }

        private suspend fun listenDocuments(
            searchText: String = "",
            typeDomain: DocumentTypeDomain,
            params: List<ParamDomain>?
        ) {
            catchException {
                dispatch(Result.IsListLoading(true))
                documentInteractor.getDocuments(
                    type = typeDomain,
                    searchQuery = searchText,
                    params = params
                )
                    .catch {
                        handleError(it)
                    }.collect {
                        dispatch(Result.Documents(it))
                        dispatch(Result.IsListLoading(false))
                    }
            }
        }

        private suspend fun createDocument( documentType: DocumentTypeDomain) {
            dispatch(Result.IsDocumentCreateLoading(true))
            val document = documentInteractor.createDocument(documentType)
            showDocument(document)
            dispatch(Result.IsDocumentCreateLoading(false))
        }

        private fun showDocument(document: DocumentDomain) {
            publish(
                DocumentStore.Label.ShowDocumentCreate(
                    document = document
                )
            )
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.IsListLoading(false))
            publish(DocumentStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }

    }

    private sealed class Result {
        data class IsDocumentCreateLoading(val isLoading: Boolean) : Result()
        data class IsListLoading(val isLoading: Boolean) : Result()
        data class Documents(val documents: List<DocumentView>) : Result()
        data class RotatedDate(val rotatedDates: List<String>) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class FilterParams(val params: List<ParamDomain>) : Result()
    }

    private object ReducerImpl : Reducer<DocumentStore.State, Result> {
        override fun DocumentStore.State.reduce(result: Result) =
            when (result) {
                is Result.Documents -> copy(documents = result.documents)
                is Result.RotatedDate -> copy(rotatedDates = result.rotatedDates)
                is Result.IsDocumentCreateLoading -> copy(isDocumentCreateLoading = result.isLoading)
                is Result.IsListLoading -> copy(isListLoading = result.isLoading)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.FilterParams -> copy(params = result.params)
            }
    }
}