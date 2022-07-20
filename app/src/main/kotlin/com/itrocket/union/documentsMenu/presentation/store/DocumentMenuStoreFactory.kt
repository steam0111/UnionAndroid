package com.itrocket.union.documentsMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.documentsMenu.domain.DocumentMenuInteractor
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain

class DocumentMenuStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val documentMenuInteractor: DocumentMenuInteractor,
    private val authMainInteractor: AuthMainInteractor,
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
        BaseExecutor<DocumentMenuStore.Intent, Unit, DocumentMenuStore.State, Result, DocumentMenuStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> DocumentMenuStore.State
        ) {
            dispatch(Result.Documents(documentMenuInteractor.getDocuments(), 0))
        }

        override suspend fun executeIntent(
            intent: DocumentMenuStore.Intent,
            getState: () -> DocumentMenuStore.State
        ) {
            when (intent) {
                is DocumentMenuStore.Intent.OnDocumentClicked -> {
                    val newDocuments = documentMenuInteractor.getDocuments(intent.item)
                    if (newDocuments.isEmpty()) {
                        publish(DocumentMenuStore.Label.ShowDocumentDetail(intent.item))
                    } else {
                        dispatch(Result.Documents(newDocuments, getState().menuDeepLevel + 1))
                    }
                }
                DocumentMenuStore.Intent.OnProfileClicked -> {
                    // no-op
                }
                DocumentMenuStore.Intent.OnLogoutClicked -> {
                    dispatch(Result.Loading(true))
                    catchException {
                        authMainInteractor.logout()
                        publish(DocumentMenuStore.Label.ShowAuth)
                    }
                    dispatch(Result.Loading(false))
                }
                DocumentMenuStore.Intent.OnSettingsClicked -> publish(DocumentMenuStore.Label.ShowSettings)
                DocumentMenuStore.Intent.OnBackClicked -> {
                    if (getState().menuDeepLevel == 1) {
                        dispatch(Result.Documents(documentMenuInteractor.getDocuments(), 0))
                    } else if (getState().menuDeepLevel == 0) {
                        publish(DocumentMenuStore.Label.GoBack)
                    }
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(DocumentMenuStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Documents(val documents: List<DocumentMenuDomain>, val menuDeepLevel: Int) :
            Result()

        data class Loading(val loading: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<DocumentMenuStore.State, Result> {
        override fun DocumentMenuStore.State.reduce(result: Result) =
            when (result) {
                is Result.Documents -> copy(
                    documents = result.documents,
                    menuDeepLevel = result.menuDeepLevel
                )
                is Result.Loading -> copy(loading = result.loading)
            }
    }
}