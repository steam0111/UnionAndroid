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
import com.itrocket.union.documents.presentation.view.DocumentView
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
            dispatch(Result.IsLoading(true))
            delay(1000)
            dispatch(Result.Documents(documentInteractor.getDocuments()))
            dispatch(Result.IsLoading(false))
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
                    //no-op
                }
                DocumentStore.Intent.OnDocumentCreateClicked -> {
                    //no-op
                }
                is DocumentStore.Intent.OnDateArrowClicked -> {
                    val newRotatedDates =
                        documentInteractor.resolveRotatedDates(getState().rotatedDates, intent.date)
                    dispatch(Result.RotatedDate(newRotatedDates))
                }
            }
        }
    }

    private sealed class Result {
        data class IsLoading(val isLoading: Boolean) : Result()
        data class Documents(val documents: List<DocumentView>) : Result()
        data class RotatedDate(val rotatedDates: List<String>) : Result()
    }

    private object ReducerImpl : Reducer<DocumentStore.State, Result> {
        override fun DocumentStore.State.reduce(result: Result) =
            when (result) {
                is Result.IsLoading -> copy(isLoading = result.isLoading)
                is Result.Documents -> copy(documents = result.documents)
                is Result.RotatedDate -> copy(rotatedDates = result.rotatedDates)
            }
    }
}