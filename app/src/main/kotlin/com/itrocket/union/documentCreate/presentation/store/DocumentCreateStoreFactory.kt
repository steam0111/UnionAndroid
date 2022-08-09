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
import com.itrocket.union.documentCreate.presentation.view.DocumentConfirmAlertType
import com.itrocket.union.documents.data.mapper.getParams
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.Params
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.selectParams.domain.SelectParamsInteractor
import com.itrocket.union.utils.ifBlankOrNull

class DocumentCreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val documentCreateInteractor: DocumentCreateInteractor,
    private val documentCreateArguments: DocumentCreateArguments,
    private val documentAccountingObjectManager: DocumentAccountingObjectManager,
    private val documentReservesManager: DocumentReservesManager,
    private val errorInteractor: ErrorInteractor,
    private val filterInteractor: FilterInteractor,
    private val selectParamsInteractor: SelectParamsInteractor
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
                val docArgument = documentCreateArguments.document
                val document = if (docArgument.isDocumentExists) {
                    docArgument.id?.let { documentCreateInteractor.getDocumentById(it) }
                } else {
                    null
                }

                document?.let { dispatch(Result.Document(it)) }
                changeParams(
                    getState().document.params,
                    documentTypeDomain = getState().document.documentType
                )

                dispatch(Result.AccountingObjects(document?.accountingObjects ?: listOf()))
                dispatch(Result.Reserves(document?.reserves ?: listOf()))

            }
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: DocumentCreateStore.Intent,
            getState: () -> DocumentCreateStore.State
        ) {
            when (intent) {
                DocumentCreateStore.Intent.OnBackClicked -> publish(DocumentCreateStore.Label.GoBack)
                DocumentCreateStore.Intent.OnChooseAccountingObjectClicked -> onChooseAccountingObjectClicked(
                    getState
                )
                DocumentCreateStore.Intent.OnChooseReserveClicked -> onChooseReserveClicked(getState)
                DocumentCreateStore.Intent.OnDropClicked -> {
                    changeParams(
                        getState().document.params,
                        documentTypeDomain = getState().document.documentType
                    )
                    dispatch(Result.AccountingObjects(getState().document.accountingObjects))
                    dispatch(Result.Reserves(getState().document.reserves))
                }
                is DocumentCreateStore.Intent.OnParamClicked -> {
                    if (!getState().document.isStatusCompleted) {
                        showParams(
                            params = getState().params,
                            param = intent.param
                        )
                    }
                }
                is DocumentCreateStore.Intent.OnParamCrossClicked -> {
                    val params = documentCreateInteractor.clearParam(
                        getState().params,
                        intent.param
                    )
                    changeParams(
                        params = params,
                        documentTypeDomain = getState().document.documentType
                    )
                }
                is DocumentCreateStore.Intent.OnParamsChanged -> {
                    val params = documentCreateInteractor.changeParams(
                        getState().params,
                        intent.params
                    )
                    changeParams(
                        params = params,
                        documentTypeDomain = getState().document.documentType
                    )
                }
                DocumentCreateStore.Intent.OnSaveClicked -> {
                    dispatch(Result.ConfirmDialogType(DocumentConfirmAlertType.SAVE))
                }
                is DocumentCreateStore.Intent.OnSelectPage -> dispatch(Result.SelectPage(intent.selectedPage))
                DocumentCreateStore.Intent.OnSettingsClicked -> publish(DocumentCreateStore.Label.ShowReadingMode)
                is DocumentCreateStore.Intent.OnLocationChanged -> {
                    val params = documentCreateInteractor.changeLocation(
                        getState().params,
                        intent.location.location
                    )
                    changeParams(
                        params = params,
                        documentTypeDomain = getState().document.documentType
                    )
                }
                is DocumentCreateStore.Intent.OnStructuralChanged -> {
                    val params = documentCreateInteractor.changeStructural(
                        getState().params,
                        intent.structural.structural
                    )
                    changeParams(
                        params = params,
                        documentTypeDomain = getState().document.documentType
                    )
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
                is DocumentCreateStore.Intent.OnNewAccountingObjectRfidHandled -> handleRfidsAccountingObjects(
                    intent.rfid,
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
                DocumentCreateStore.Intent.OnCompleteClicked -> {
                    dispatch(Result.ConfirmDialogType(DocumentConfirmAlertType.CONDUCT))
                }
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
                is DocumentCreateStore.Intent.OnDismissConfirmDialog -> {
                    dispatch(Result.ConfirmDialogType(DocumentConfirmAlertType.NONE))
                }
                is DocumentCreateStore.Intent.OnConfirmActionClick -> {
                    handleOnConfirmActionClick(getState())
                }
            }
        }

        private suspend fun handleOnConfirmActionClick(state: DocumentCreateStore.State) {
            if (state.confirmDialogType == DocumentConfirmAlertType.SAVE) {
                saveDocument(state)
            } else if (state.confirmDialogType == DocumentConfirmAlertType.CONDUCT) {
                conductDocument(state)
            }
            dispatch(Result.ConfirmDialogType(DocumentConfirmAlertType.NONE))
        }

        private suspend fun conductDocument(state: DocumentCreateStore.State) {
            val documentId = documentCreateInteractor.createOrUpdateDocument(
                document = state.document,
                accountingObjects = state.accountingObjects,
                params = state.params,
                reserves = state.reserves,
                status = DocumentStatus.COMPLETED
            )
            documentAccountingObjectManager.changeAccountingObjectsAfterConduct(
                documentTypeDomain = state.document.documentType,
                accountingObjects = state.accountingObjects,
                params = state.params
            )
            documentReservesManager.changeReservesAfterConduct(
                documentId = documentId,
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
            rfid: String,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            val newAccountingObjects = documentCreateInteractor.handleNewAccountingObjectRfids(
                accountingObjects = accountingObjects,
                handledAccountingObjectRfid = rfid
            )
            dispatch(Result.AccountingObjects(newAccountingObjects))
        }

        private suspend fun changeParams(
            params: List<ParamDomain>,
            documentTypeDomain: DocumentTypeDomain
        ) {
            val filters = params.ifEmpty {
                selectParamsInteractor.getInitialDocumentParams(
                    getParams(documentType = documentCreateArguments.document.documentType.name)
                )
            }

            dispatch(Result.Params(filters))
            dispatch(
                Result.ParamsValid(
                    documentCreateInteractor.isParamsValid(
                        filters,
                        documentTypeDomain
                    )
                )
            )
        }

        private suspend fun saveDocument(state: DocumentCreateStore.State) {
            val documentId = documentCreateInteractor.createOrUpdateDocument(
                accountingObjects = state.accountingObjects,
                document = state.document,
                params = state.params,
                reserves = state.reserves,
                status = state.document.documentStatus
            )
            dispatch(Result.Document(state.document.copy(id = documentId)))
        }

        private fun showParams(params: List<ParamDomain>, param: ParamDomain) {
            when (param.type) {
                ManualType.LOCATION, ManualType.LOCATION_FROM,
                ManualType.RELOCATION_LOCATION_TO, ManualType.LOCATION_TO -> publish(
                    DocumentCreateStore.Label.ShowLocation(param as LocationParamDomain)
                )
                ManualType.STRUCTURAL -> publish(DocumentCreateStore.Label.ShowStructural(param as StructuralParamDomain))
                else -> {
                    val defaultTypeParams =
                        filterInteractor.getDefaultTypeParams(Params(params))
                    val currentStep = defaultTypeParams.indexOf(param) + 1
                    publish(
                        DocumentCreateStore.Label.ShowParamSteps(
                            currentStep = currentStep,
                            params = defaultTypeParams
                        )
                    )
                }
            }
        }

        private suspend fun onChooseAccountingObjectClicked(getState: () -> DocumentCreateStore.State) {
            publish(
                DocumentCreateStore.Label.ShowAccountingObjects(
                    listOf(),
                    documentCreateInteractor.getAccountingObjectIds(getState().accountingObjects)
                )
            )
        }

        private suspend fun onChooseReserveClicked(getState: () -> DocumentCreateStore.State) {
            publish(
                DocumentCreateStore.Label.ShowReserves(
                    listOf(),
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
        data class ParamsValid(val isParamsValid: Boolean) : Result()
        data class SelectPage(val page: Int) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
        data class Reserves(val reserves: List<ReservesDomain>) : Result()
        data class ConfirmDialogType(val type: DocumentConfirmAlertType) : Result()
    }

    private object ReducerImpl : Reducer<DocumentCreateStore.State, Result> {
        override fun DocumentCreateStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.AccountingObjects -> copy(accountingObjects = result.accountingObjects)
                is Result.Params -> copy(params = result.params)
                is Result.Reserves -> copy(reserves = result.reserves)
                is Result.SelectPage -> copy(selectedPage = result.page)
                is Result.Document -> copy(document = result.document)
                is Result.ParamsValid -> copy(isParamsValid = result.isParamsValid)
                is Result.ConfirmDialogType -> copy(confirmDialogType = result.type)
            }
    }

    companion object {
        const val PARAMS_PAGE = 0
        const val ACCOUNTING_OBJECT_PAGE = 1
        const val RESERVES_PAGE = 2
    }
}