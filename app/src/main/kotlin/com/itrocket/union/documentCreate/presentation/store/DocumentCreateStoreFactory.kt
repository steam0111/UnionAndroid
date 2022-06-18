package com.itrocket.union.documentCreate.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documentCreate.domain.DocumentCreateInteractor
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain

class DocumentCreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val documentCreateInteractor: DocumentCreateInteractor,
    private val documentCreateArguments: DocumentCreateArguments
) {
    fun create(): DocumentCreateStore =
        object : DocumentCreateStore,
            Store<DocumentCreateStore.Intent, DocumentCreateStore.State, DocumentCreateStore.Label> by storeFactory.create(
                name = "DocumentCreateStore",
                initialState = DocumentCreateStore.State(
                    document = documentCreateArguments.document,
                    accountingObjects = documentCreateArguments.document.accountingObjects,
                    params = documentCreateArguments.document.params
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<DocumentCreateStore.Intent, Unit, DocumentCreateStore.State, Result, DocumentCreateStore.Label> =
        DocumentCreateExecutor()

    private inner class DocumentCreateExecutor :
        BaseExecutor<DocumentCreateStore.Intent, Unit, DocumentCreateStore.State, Result, DocumentCreateStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> DocumentCreateStore.State
        ) {
            isNextEnabled(getState)
        }

        override suspend fun executeIntent(
            intent: DocumentCreateStore.Intent,
            getState: () -> DocumentCreateStore.State
        ) {
            when (intent) {
                DocumentCreateStore.Intent.OnBackClicked -> publish(DocumentCreateStore.Label.GoBack)
                DocumentCreateStore.Intent.OnChooseClicked -> publish(
                    DocumentCreateStore.Label.ShowAccountingObjects(
                        getState().params
                    )
                )
                DocumentCreateStore.Intent.OnDropClicked -> {
                    dispatch(Result.Params(getState().document.params))
                    dispatch(Result.AccountingObjects(getState().document.accountingObjects))
                    isNextEnabled(getState)
                }
                DocumentCreateStore.Intent.OnNextClicked -> dispatch(
                    Result.SelectPage(
                        ACCOUNTING_OBJECT_PAGE
                    )
                )
                is DocumentCreateStore.Intent.OnParamClicked -> showParams(
                    params = getState().params,
                    param = intent.param
                )
                is DocumentCreateStore.Intent.OnParamCrossClicked -> {
                    val params = documentCreateInteractor.clearParam(
                        getState().params,
                        intent.param
                    )
                    changeParams(params = params, getState = getState)
                }
                is DocumentCreateStore.Intent.OnParamsChanged -> {
                    val params = documentCreateInteractor.changeParams(
                        getState().params,
                        intent.params
                    )
                    changeParams(params = params, getState = getState)
                }
                DocumentCreateStore.Intent.OnPrevClicked -> dispatch(Result.SelectPage(PARAMS_PAGE))
                DocumentCreateStore.Intent.OnSaveClicked -> saveDocument(getState)
                is DocumentCreateStore.Intent.OnSelectPage -> dispatch(Result.SelectPage(intent.selectedPage))
                DocumentCreateStore.Intent.OnSettingsClicked -> {
                    //no-op
                }
                is DocumentCreateStore.Intent.OnLocationChanged -> {
                    val params = documentCreateInteractor.changeLocation(
                        getState().params,
                        intent.location
                    )
                    changeParams(params = params, getState = getState)
                }
                is DocumentCreateStore.Intent.OnAccountingObjectSelected -> {
                    dispatch(
                        Result.AccountingObjects(
                            documentCreateInteractor.addAccountingObject(
                                accountingObjects = getState().accountingObjects,
                                accountingObjectDomain = intent.accountingObjectDomain
                            )
                        )
                    )
                }
            }
        }

        private fun changeParams(
            params: List<ParamDomain>,
            getState: () -> DocumentCreateStore.State
        ) {
            dispatch(Result.Params(params))
            isNextEnabled(getState)
        }

        private fun isNextEnabled(getState: () -> DocumentCreateStore.State) {
            dispatch(Result.Enabled(documentCreateInteractor.isParamsFilled(getState().params)))
        }

        private suspend fun saveDocument(getState: () -> DocumentCreateStore.State) {
            if (getState().document.objectType == ObjectType.MAIN_ASSETS) {
                documentCreateInteractor.saveAccountingObjectDocument(
                    accountingObjects = getState().accountingObjects,
                    document = getState().document,
                    params = getState().params
                )
            } else {
                documentCreateInteractor.saveReservesDocument(
                    reserves = getState().document.reserves,
                    params = getState().params
                )
            }
        }

        private fun showParams(params: List<ParamDomain>, param: ParamDomain) {
            if (param.type == ManualType.LOCATION) {
                publish(
                    DocumentCreateStore.Label.ShowLocation(param.value)
                )
            } else {
                publish(
                    DocumentCreateStore.Label.ShowParamSteps(
                        currentStep = params.indexOf(param) + 1,
                        params = params.filter { it.type != ManualType.LOCATION }
                    )
                )
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(DocumentCreateStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Params(val params: List<ParamDomain>) : Result()
        data class SelectPage(val page: Int) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
        data class Reserves(val reserves: List<ReservesDomain>) : Result()
        data class Enabled(val enabled: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<DocumentCreateStore.State, Result> {
        override fun DocumentCreateStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.AccountingObjects -> copy(accountingObjects = result.accountingObjects)
                is Result.Params -> copy(params = result.params)
                is Result.Reserves -> copy(document = document.copy(reserves = result.reserves))
                is Result.SelectPage -> copy(selectedPage = result.page)
                is Result.Enabled -> copy(isNextEnabled = result.enabled)
            }
    }

    companion object {
        const val PARAMS_PAGE = 0
        const val ACCOUNTING_OBJECT_PAGE = 1
    }
}