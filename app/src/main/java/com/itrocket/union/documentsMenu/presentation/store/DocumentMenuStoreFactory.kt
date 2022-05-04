package com.itrocket.union.documentsMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documentsMenu.domain.DocumentMenuInteractor
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain

class DocumentMenuStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val documentMenuInteractor: DocumentMenuInteractor
) {
    fun create(): DocumentMenuStore =
        object : DocumentMenuStore,
            Store<DocumentMenuStore.Intent, DocumentMenuStore.State, DocumentMenuStore.Label> by storeFactory.create(
                name = "DocumentStore",
                initialState = DocumentMenuStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<DocumentMenuStore.Intent, Unit, DocumentMenuStore.State, Result, DocumentMenuStore.Label> =
        DocumentExecutor()

    private inner class DocumentExecutor :
        SuspendExecutor<DocumentMenuStore.Intent, Unit, DocumentMenuStore.State, Result, DocumentMenuStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> DocumentMenuStore.State
        ) {
            dispatch(Result.Documents(documentMenuInteractor.getDocuments()))
        }

        override suspend fun executeIntent(
            intent: DocumentMenuStore.Intent,
            getState: () -> DocumentMenuStore.State
        ) {
            when (intent) {
                is DocumentMenuStore.Intent.OnDocumentClicked -> {
                    // no-op
                }
                DocumentMenuStore.Intent.OnProfileClicked -> {
                    // no-op
                }
            }
        }
    }

    private sealed class Result {
        data class Documents(val documents: List<DocumentMenuDomain>) : Result()
    }

    private object ReducerImpl : Reducer<DocumentMenuStore.State, Result> {
        override fun DocumentMenuStore.State.reduce(result: Result) =
            when (result) {
                is Result.Documents -> copy(documents = result.documents)
            }
    }
}