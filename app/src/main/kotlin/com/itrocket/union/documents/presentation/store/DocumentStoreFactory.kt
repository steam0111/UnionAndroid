package com.itrocket.union.documents.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.documents.domain.DocumentInteractor
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.documents.presentation.view.DocumentView
import com.itrocket.union.documents.presentation.view.toDocumentDomain
import com.itrocket.utils.resolveItem
import kotlinx.coroutines.delay

class DocumentStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val documentInteractor: DocumentInteractor,
    private val arguments: DocumentArguments
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
        SuspendExecutor<DocumentStore.Intent, Unit, DocumentStore.State, Result, DocumentStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> DocumentStore.State
        ) {
            dispatch(Result.IsListLoading(true))
            delay(1000)
            dispatch(Result.Documents(documentInteractor.getDocuments(getState().type)))
            dispatch(Result.IsListLoading(false))
        }

        override suspend fun executeIntent(
            intent: DocumentStore.Intent,
            getState: () -> DocumentStore.State
        ) {
            when (intent) {
                DocumentStore.Intent.OnArrowBackClicked -> publish(DocumentStore.Label.GoBack)
                DocumentStore.Intent.OnFilterClicked -> {
                    //no-op
                }
                DocumentStore.Intent.OnSearchClicked -> {
                    //no-op
                }
                is DocumentStore.Intent.OnDocumentClicked -> {
                    showDocument(intent.documentView.toDocumentDomain())
                }
                DocumentStore.Intent.OnDocumentCreateClicked -> createDocument(
                    listType = ObjectType.MAIN_ASSETS,
                    documentType = getState().type
                )
                is DocumentStore.Intent.OnDateArrowClicked -> {
                    val newRotatedDates =
                        documentInteractor.resolveRotatedDates(getState().rotatedDates, intent.date)
                    dispatch(Result.RotatedDate(newRotatedDates))
                }
            }

        }

        private suspend fun createDocument(listType: ObjectType, documentType: DocumentTypeDomain) {
            dispatch(Result.IsDocumentCreateLoading(true))
            val document = documentInteractor.createDocument(documentType, listType)
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
    }

    private sealed class Result {
        data class IsDocumentCreateLoading(val isLoading: Boolean) : Result()
        data class IsListLoading(val isLoading: Boolean) : Result()
        data class Documents(val documents: List<DocumentView>) : Result()
        data class RotatedDate(val rotatedDates: List<String>) : Result()
    }

    private object ReducerImpl : Reducer<DocumentStore.State, Result> {
        override fun DocumentStore.State.reduce(result: Result) =
            when (result) {
                is Result.Documents -> copy(documents = result.documents)
                is Result.RotatedDate -> copy(rotatedDates = result.rotatedDates)
                is Result.IsDocumentCreateLoading -> copy(isDocumentCreateLoading = result.isLoading)
                is Result.IsListLoading -> copy(isListLoading = result.isLoading)
            }
    }
}