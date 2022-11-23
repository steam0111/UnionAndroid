package com.itrocket.union.documentsMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.common.DrawerScreenType
import com.itrocket.union.documentsMenu.domain.DocumentMenuInteractor
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.syncAll.domain.SyncAllInteractor

class DocumentMenuStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val documentMenuInteractor: DocumentMenuInteractor,
    private val authMainInteractor: AuthMainInteractor,
    private val syncAllInteractor: SyncAllInteractor,
    private val errorInteractor: ErrorInteractor
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
            getUsername()
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
                is DocumentMenuStore.Intent.OnDrawerDestinationClick -> {
                    when (intent.type) {
                        DrawerScreenType.SETTINGS -> {
                            publish(DocumentMenuStore.Label.ShowSettings)
                        }
                        DrawerScreenType.LOGOUT -> dispatch(Result.DialogType(AlertType.SYNC))
                        DrawerScreenType.SYNC -> publish(DocumentMenuStore.Label.ShowSync(false))
                    }
                }
                DocumentMenuStore.Intent.OnBackClicked -> {
                    if (getState().menuDeepLevel == 1) {
                        dispatch(Result.Documents(documentMenuInteractor.getDocuments(), 0))
                    } else if (getState().menuDeepLevel == 0) {
                        publish(DocumentMenuStore.Label.GoBack)
                    }
                }
                DocumentMenuStore.Intent.OnConfirmLogoutClicked -> logout()
                DocumentMenuStore.Intent.OnConfirmSyncClicked -> {
                    dispatch(Result.DialogType(AlertType.NONE))
                    publish(DocumentMenuStore.Label.ShowSync(true))
                }
                DocumentMenuStore.Intent.OnDismissLogoutClicked -> dispatch(
                    Result.DialogType(
                        AlertType.NONE
                    )
                )
                DocumentMenuStore.Intent.OnDismissSyncClicked -> dispatch(
                    Result.DialogType(
                        AlertType.LOGOUT
                    )
                )
            }
        }

        private suspend fun logout() {
            dispatch(Result.Loading(true))
            catchException {
                syncAllInteractor.clearAll()
                authMainInteractor.logout()
            }
            dispatch(Result.Loading(false))
        }

        override fun handleError(throwable: Throwable) {
            publish(DocumentMenuStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        private suspend fun getUsername() {
            val currentEmployeeId = authMainInteractor.getMyConfig().employeeId
            val user = documentMenuInteractor.getUsername(currentEmployeeId)
            user?.let {
                dispatch(Result.Username(fullName = it.fullName))
            }
        }
    }

    private sealed class Result {
        data class Documents(val documents: List<DocumentMenuDomain>, val menuDeepLevel: Int) :
            Result()

        data class Username(val fullName: String, ) : Result()

        data class Loading(val loading: Boolean) : Result()
        data class DialogType(val dialogType: AlertType) : Result()
    }

    private object ReducerImpl : Reducer<DocumentMenuStore.State, Result> {
        override fun DocumentMenuStore.State.reduce(result: Result) =
            when (result) {
                is Result.Documents -> copy(
                    documents = result.documents,
                    menuDeepLevel = result.menuDeepLevel
                )
                is Result.Loading -> copy(loading = result.loading)
                is Result.Username -> copy(fullName = result.fullName)
                is Result.DialogType -> copy(dialogType = result.dialogType)
            }
    }
}