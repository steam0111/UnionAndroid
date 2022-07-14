package com.itrocket.union.documentCreate.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documentCreate.domain.DocumentAccountingObjectManager
import com.itrocket.union.documentCreate.domain.DocumentCreateInteractor
import com.itrocket.union.documentCreate.domain.DocumentReservesManager
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.utils.ifBlankOrNull

class DocumentCreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val documentCreateInteractor: DocumentCreateInteractor,
    private val documentCreateArguments: DocumentCreateArguments,
    private val documentAccountingObjectManager: DocumentAccountingObjectManager,
    private val documentReservesManager: DocumentReservesManager,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): DocumentCreateStore =
        object : DocumentCreateStore,
            Store<DocumentCreateStore.Intent, DocumentCreateStore.State, DocumentCreateStore.Label> by storeFactory.create(
                name = "DocumentCreateStore",
                initialState = DocumentCreateStore.State(
                    document = documentCreateArguments.document,
                    accountingObjects = documentCreateArguments.document.accountingObjects,
                    params = documentCreateArguments.document.params,
                    reserves = documentCreateArguments.document.reserves,
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
            dispatch(Result.Loading(true))
            catchException {
                val document = documentCreateInteractor.getDocumentById(
                    documentCreateArguments.document.number
                )
                dispatch(Result.Document(document))
                dispatch(Result.AccountingObjects(document.accountingObjects))
                dispatch(Result.Reserves(document.reserves))
                dispatch(Result.Params(document.params))
            }
            dispatch(Result.Loading(false))
            isNextEnabled(getState)
        }

        override suspend fun executeIntent(
            intent: DocumentCreateStore.Intent,
            getState: () -> DocumentCreateStore.State
        ) {
            when (intent) {
                DocumentCreateStore.Intent.OnBackClicked -> publish(DocumentCreateStore.Label.GoBack)
                DocumentCreateStore.Intent.OnChooseAccountingObjectClicked -> onChooseAccountingObjectClicked(getState)
                DocumentCreateStore.Intent.OnChooseReserveClicked -> onChooseReserveClicked(getState)
                DocumentCreateStore.Intent.OnDropClicked -> {
                    changeParams(getState().document.params, getState)
                    dispatch(Result.AccountingObjects(getState().document.accountingObjects))
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
                DocumentCreateStore.Intent.OnSettingsClicked -> publish(DocumentCreateStore.Label.ShowReadingMode)
                is DocumentCreateStore.Intent.OnLocationChanged -> {
                    val params = documentCreateInteractor.changeLocation(
                        getState().params,
                        intent.location.location
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
                is DocumentCreateStore.Intent.OnNewAccountingObjectBarcodeHandled -> handleBarcodeAccountingObjects(
                    intent.barcode,
                    getState().accountingObjects
                )
                is DocumentCreateStore.Intent.OnNewAccountingObjectRfidsHandled -> handleRfidsAccountingObjects(
                    intent.rfids,
                    getState().accountingObjects
                )
                is DocumentCreateStore.Intent.OnReserveSelected -> dispatch(
                    Result.Reserves(
                        documentCreateInteractor.addReserve(
                            reserves = getState().reserves,
                            reserve = intent.reserve
                        )
                    )
                )
                DocumentCreateStore.Intent.OnCompleteClicked -> conductDocument(getState())
                is DocumentCreateStore.Intent.OnReserveCountSelected -> dispatch(
                    Result.Reserves(
                        documentCreateInteractor.updateReserveCount(
                            reserves = getState().reserves,
                            id = intent.result.id,
                            count = intent.result.count
                        )
                    )
                )
                is DocumentCreateStore.Intent.OnReserveClicked -> publish(
                    DocumentCreateStore.Label.ShowSelectCount(
                        id = intent.reserve.id,
                        count = intent.reserve.itemsCount
                    )
                )
            }
        }

        private suspend fun conductDocument(state: DocumentCreateStore.State) {
            documentCreateInteractor.conductDocument(
                document = state.document,
                accountingObjects = state.accountingObjects,
                params = state.params,
                reserves = state.reserves
            )
            documentAccountingObjectManager.changeAccountingObjectsAfterConduct(
                documentTypeDomain = state.document.documentType,
                accountingObjects = state.accountingObjects,
                params = state.params
            )
            documentReservesManager.changeReservesAfterConduct(
                documentId = state.document.number,
                documentTypeDomain = state.document.documentType,
                reserves = state.reserves,
                params = state.params
            )
            dispatch(Result.Document(state.document.copy(documentStatus = DocumentStatus.COMPLETED)))
        }

        private suspend fun handleBarcodeAccountingObjects(
            barcode: String,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            val newAccountingObjects = documentCreateInteractor.handleNewAccountingObjectBarcode(
                accountingObjects = accountingObjects,
                barcode = barcode
            )
            dispatch(Result.AccountingObjects(newAccountingObjects))
        }

        private suspend fun handleRfidsAccountingObjects(
            rfids: List<String>,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            val newAccountingObjects = documentCreateInteractor.handleNewAccountingObjectRfids(
                accountingObjects = accountingObjects,
                handledAccountingObjectRfids = rfids
            )
            dispatch(Result.AccountingObjects(newAccountingObjects))
        }

        private fun changeParams(
            params: List<ParamDomain>,
            getState: () -> DocumentCreateStore.State
        ) {
            dispatch(Result.Params(params))
            isNextEnabled(getState)
        }

        private fun isNextEnabled(getState: () -> DocumentCreateStore.State) {
            dispatch(
                Result.Enabled(
                    documentCreateInteractor.isParamsValid(
                        getState().params,
                        getState().document.documentType
                    )
                )
            )
        }

        private suspend fun saveDocument(getState: () -> DocumentCreateStore.State) {
            documentCreateInteractor.saveDocument(
                accountingObjects = getState().accountingObjects,
                document = getState().document,
                params = getState().params,
                reserves = getState().reserves
            )
            publish(DocumentCreateStore.Label.GoBack)
        }

        private fun showParams(params: List<ParamDomain>, param: ParamDomain) {
            if (param.type == ManualType.LOCATION) {
                publish(
                    DocumentCreateStore.Label.ShowLocation(param as LocationParamDomain)
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

        private suspend fun onChooseAccountingObjectClicked(getState: () -> DocumentCreateStore.State) {
            publish(
                DocumentCreateStore.Label.ShowAccountingObjects(
                    listOf(),//documentCreateInteractor.getFilterParams(getState().params),
                    documentCreateInteractor.getAccountingObjectIds(getState().accountingObjects)
                )
            )
        }

        private suspend fun onChooseReserveClicked(getState: () -> DocumentCreateStore.State) {
            publish(
                DocumentCreateStore.Label.ShowReserves(
                    listOf(),//documentCreateInteractor.getFilterParams(getState().params),
                    documentCreateInteractor.getReservesIds(getState().reserves)
                )
            )
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(DocumentCreateStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Document(val document: DocumentDomain) : Result()
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
                is Result.Reserves -> copy(reserves = result.reserves)
                is Result.SelectPage -> copy(selectedPage = result.page)
                is Result.Enabled -> copy(isNextEnabled = result.enabled)
                is Result.Document -> copy(document = result.document)
            }
    }

    companion object {
        const val PARAMS_PAGE = 0
        const val ACCOUNTING_OBJECT_PAGE = 1
    }
}