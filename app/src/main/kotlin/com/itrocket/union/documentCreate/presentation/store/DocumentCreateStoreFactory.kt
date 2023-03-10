package com.itrocket.union.documentCreate.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.documentCreate.domain.DocumentAccountingObjectManager
import com.itrocket.union.documentCreate.domain.DocumentCreateInteractor
import com.itrocket.union.documentCreate.domain.DocumentReservesManager
import com.itrocket.union.documents.data.mapper.getParams
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.CheckBoxParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.moduleSettings.domain.ModuleSettingsInteractor
import com.itrocket.union.nfcReader.presentation.store.NfcReaderResult
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.selectParams.domain.SelectParamsInteractor
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission

class DocumentCreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val documentCreateInteractor: DocumentCreateInteractor,
    private val documentCreateArguments: DocumentCreateArguments,
    private val errorInteractor: ErrorInteractor,
    private val selectParamsInteractor: SelectParamsInteractor,
    private val documentAccountingObjectManager: DocumentAccountingObjectManager,
    private val documentReservesManager: DocumentReservesManager,
    private val unionPermissionsInteractor: UnionPermissionsInteractor,
    private val moduleSettingsInteractor: ModuleSettingsInteractor
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
            dispatch(Result.ReadingMode(moduleSettingsInteractor.getDefaultReadingMode(isForceUpdate = true)))
            dispatch(Result.CanUpdate(unionPermissionsInteractor.canUpdate(UnionPermission.ALL_DOCUMENTS)))
            dispatch(Result.CanCreate(unionPermissionsInteractor.canCreate(UnionPermission.ALL_DOCUMENTS)))
            catchException {
                val docArgument = documentCreateArguments.document
                val document = if (docArgument.isDocumentExists) {
                    docArgument.id?.let { documentCreateInteractor.getDocumentById(it) }
                } else {
                    null
                }
                val isDocumentChangeEnabled = if (document == null) {
                    getState().canCreate
                } else {
                    getState().canUpdate && document.documentStatus != DocumentStatus.COMPLETED
                }

                dispatch(Result.IsDocumentChangeEnabled(isDocumentChangeEnabled))
                document?.let { dispatch(Result.Document(it)) }
                dispatch(Result.CanDelete(getState().canUpdate && !getState().document.isStatusCompleted))
                changeParams(getState().document.params)

                val params = documentCreateInteractor.changeParams(
                    oldParams = getState().params,
                    newParams = getState().params,
                    documentType = getState().document.documentType
                )
                dispatch(Result.Params(params))
                dispatch(Result.AccountingObjects(document?.accountingObjects ?: listOf()))
                tryChangeReturnExploiting(getState)
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
                DocumentCreateStore.Intent.OnDropClicked -> onDropClicked(getState)
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
                        intent.param,
                        getState().document.documentType
                    )
                    changeParams(params = params)
                }

                is DocumentCreateStore.Intent.OnParamsChanged -> {
                    val params = documentCreateInteractor.changeParams(
                        oldParams = getState().params,
                        newParams = intent.params,
                        documentType = getState().document.documentType
                    )
                    changeParams(params = params)
                }

                DocumentCreateStore.Intent.OnSaveClicked -> {
                    dispatch(Result.ConfirmDialogType(AlertType.SAVE))
                }

                is DocumentCreateStore.Intent.OnSelectPage -> dispatch(Result.SelectPage(intent.selectedPage))
                DocumentCreateStore.Intent.OnSettingsClicked -> publish(DocumentCreateStore.Label.ShowReadingMode)
                is DocumentCreateStore.Intent.OnAccountingObjectSelected -> onAccountingObjectSelected(
                    getState = getState,
                    accountingObject = intent.accountingObjectDomain
                )

                is DocumentCreateStore.Intent.OnNewAccountingObjectBarcodeHandled -> handleBarcodeAccountingObjects(
                    barcode = intent.barcode,
                    accountingObjects = getState().accountingObjects,
                    readingModeTab = getState().readingModeTab,
                    getState = getState
                )

                is DocumentCreateStore.Intent.OnNewAccountingObjectRfidHandled -> handleRfidsAccountingObjects(
                    rfids = intent.rfids,
                    accountingObjects = getState().accountingObjects,
                    getState = getState
                )

                is DocumentCreateStore.Intent.OnReserveSelected -> onReserveSelected(
                    reserves = getState().reserves,
                    newReserve = intent.reserve
                )

                DocumentCreateStore.Intent.OnCompleteClicked -> {
                    dispatch(Result.ConfirmDialogType(AlertType.CONDUCT))
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
                    dispatch(Result.ConfirmDialogType(AlertType.NONE))
                }

                is DocumentCreateStore.Intent.OnConfirmActionClick -> {
                    handleOnConfirmActionClick(getState())
                }

                is DocumentCreateStore.Intent.OnNfcReaderClose -> {
                    onNfcReaderClose(intent.nfcReaderResult)
                }

                is DocumentCreateStore.Intent.OnReadingModeTabChanged -> dispatch(
                    Result.ReadingMode(
                        intent.readingModeTab
                    )
                )

                is DocumentCreateStore.Intent.OnManualInput -> onManualInput(
                    readingModeResult = intent.readingModeResult,
                    getState = getState
                )

                is DocumentCreateStore.Intent.OnDeleteAccountingObjectClicked -> deleteAccountingObject(
                    accountingObjects = getState().accountingObjects,
                    accountingObjectId = intent.accountingObjectId
                )

                is DocumentCreateStore.Intent.OnDeleteReserveClicked -> deleteReserve(
                    reserves = getState().reserves,
                    reserveId = intent.reserveId
                )

                DocumentCreateStore.Intent.OnListItemDialogDismissed -> onListItemDialogDismissed()
                is DocumentCreateStore.Intent.OnErrorHandled -> handleError(intent.throwable)
            }
        }

        private suspend fun onDropClicked(getState: () -> DocumentCreateStore.State) {
            changeParams(getState().document.params)
            dispatch(Result.AccountingObjects(getState().document.accountingObjects))
            dispatch(Result.Reserves(getState().document.reserves))
            tryChangeReturnExploiting(getState)
        }

        private suspend fun tryChangeReturnExploiting(getState: () -> DocumentCreateStore.State) {
            dispatch(
                Result.Params(
                    documentCreateInteractor.tryChangeReturnExploiting(
                        documentType = getState().document.documentType,
                        params = getState().params,
                        accountingObjects = getState().accountingObjects
                    )
                )
            )
        }

        private suspend fun onAccountingObjectSelected(
            getState: () -> DocumentCreateStore.State,
            accountingObject: AccountingObjectDomain
        ) {
            dispatch(
                Result.AccountingObjects(
                    documentCreateInteractor.addAccountingObject(
                        accountingObjects = getState().accountingObjects,
                        accountingObject = accountingObject
                    )
                )
            )
            tryChangeReturnExploiting(getState)
        }

        private fun onListItemDialogDismissed() {
            dispatch(Result.ConfirmDialogType(AlertType.NONE))
            dispatch(Result.DialogListItem(listOf()))
        }

        private fun canEditDocument(state: DocumentCreateStore.State): Boolean {
            return with(state) {
                (document.isDocumentExists && canUpdate || !document.isDocumentExists && canCreate) && document.documentStatus != DocumentStatus.COMPLETED
            }
        }

        private suspend fun deleteReserve(
            reserveId: String,
            reserves: List<ReservesDomain>
        ) {
            catchException {
                val newReserves = documentCreateInteractor.deleteReserve(
                    reserveId = reserveId,
                    reserveList = reserves
                )
                dispatch(Result.Reserves(newReserves))
            }
        }

        private suspend fun deleteAccountingObject(
            accountingObjectId: String,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            catchException {
                val newAccountingObjects = documentCreateInteractor.deleteAccountingObject(
                    accountingObjectId = accountingObjectId,
                    accountingObjectList = accountingObjects
                )
                dispatch(Result.AccountingObjects(newAccountingObjects))
            }
        }

        private suspend fun onManualInput(
            readingModeResult: ReadingModeResult,
            getState: () -> DocumentCreateStore.State
        ) {
            when (readingModeResult.readingModeTab) {
                ReadingModeTab.RFID -> {
                    //no-op
                }

                ReadingModeTab.SN, ReadingModeTab.BARCODE -> {
                    handleBarcodeAccountingObjects(
                        accountingObjects = getState().accountingObjects,
                        barcode = readingModeResult.scanData,
                        readingModeTab = getState().readingModeTab,
                        getState = getState
                    )
                }
            }
        }

        private suspend fun onNfcReaderClose(nfcReaderResult: NfcReaderResult) {
            if (nfcReaderResult.isNfcReadingSuccess) {
                catchException {
                    val document =
                        documentCreateInteractor.getDocumentById(requireNotNull(nfcReaderResult.documentId))
                    dispatch(Result.IsDocumentChangeEnabled(false))
                    dispatch(Result.Document(document))
                }
            }
        }

        private suspend fun onReserveSelected(
            reserves: List<ReservesDomain>,
            newReserve: ReservesDomain
        ) {
            dispatch(
                Result.Reserves(
                    documentCreateInteractor.addReserve(
                        reserves = reserves,
                        reserve = newReserve
                    )
                )
            )
            publish(
                DocumentCreateStore.Label.ShowSelectCount(
                    id = newReserve.id,
                    count = newReserve.itemsCount
                )
            )
        }

        private suspend fun handleOnConfirmActionClick(state: DocumentCreateStore.State) {
            val confirmDialogType = state.confirmDialogType
            val allEmptyMandatoryFields =
                state.params.filter { it.isMandatory && it.value.isEmpty() }

            if (allEmptyMandatoryFields.isNotEmpty() && confirmDialogType != AlertType.MANDATORY_FIELDS) {
                dispatch(Result.ConfirmDialogType(AlertType.MANDATORY_FIELDS))
                return
            }

            dispatch(Result.ConfirmDialogType(AlertType.NONE))

            if (confirmDialogType == AlertType.SAVE) {
                saveDocument(state)
            } else if (confirmDialogType == AlertType.CONDUCT) {
                onConductClicked(state)
            } else if (confirmDialogType == AlertType.MANDATORY_FIELDS) {
                dispatch(Result.ConfirmDialogType(AlertType.NONE))
            }
        }

        private suspend fun onConductClicked(state: DocumentCreateStore.State) {
            if (state.document.accountingObjects.firstOrNull { it.isWrittenOff } != null) {
                publish(DocumentCreateStore.Label.ShowWarning(R.string.restrict_use_written_off_ao_for_conduct))
                return
            }

            val conductPermission = UnionPermission.ALL_DOCUMENTS
            val isNotAllAccountingObjectsMarked =
                documentCreateInteractor.isNotAllAccountingObjectMarked(state.accountingObjects)
            val isDocumentTypeGive = state.document.documentType == DocumentTypeDomain.GIVE
            val isDocumentTypeRelocation =
                state.document.documentType == DocumentTypeDomain.RELOCATION
            when {
                isDocumentTypeGive && isNotAllAccountingObjectsMarked -> {
                    dispatch(Result.ConfirmDialogType(AlertType.LIST_ITEM))
                    dispatch(Result.DialogLoading(true))
                    catchException {
                        dispatch(
                            Result.DialogListItem(
                                documentCreateInteractor.filterNotMarkedAccountingObjectNames(
                                    state.accountingObjects
                                )
                            )
                        )
                    }
                    dispatch(Result.DialogLoading(false))
                }

                isDocumentTypeRelocation && state.accountingObjects.isEmpty() && state.reserves.isEmpty() -> {
                    dispatch(Result.ConfirmDialogType(AlertType.CONDUCT_RETURN))
                }

                unionPermissionsInteractor.canConductDocument(conductPermission) -> {
                    conductDocument(state)
                }

                else -> {
                    publish(
                        DocumentCreateStore.Label.ShowNfcReader(
                            documentCreateInteractor.completeDocument(
                                document = state.document,
                                accountingObjects = state.accountingObjects,
                                reserves = state.reserves,
                                params = state.params
                            )
                        )
                    )
                }
            }
        }

        private suspend fun conductDocument(state: DocumentCreateStore.State) {
            documentCreateInteractor.createOrUpdateDocument(
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
                documentTypeDomain = state.document.documentType,
                reserves = state.reserves,
                params = state.params
            )
            dispatch(Result.Document(state.document.copy(documentStatus = DocumentStatus.COMPLETED)))
            dispatch(Result.IsDocumentChangeEnabled(false))
        }

        private suspend fun handleBarcodeAccountingObjects(
            barcode: String,
            accountingObjects: List<AccountingObjectDomain>,
            readingModeTab: ReadingModeTab,
            getState: () -> DocumentCreateStore.State
        ) {
            if (canEditDocument(getState())) {
                val newAccountingObjects =
                    documentCreateInteractor.handleNewAccountingObjectBarcode(
                        accountingObjects = accountingObjects,
                        barcode = barcode,
                        isSerialNumber = readingModeTab == ReadingModeTab.SN
                    )
                dispatch(Result.AccountingObjects(newAccountingObjects))
                tryChangeReturnExploiting(getState)
            }
        }

        private suspend fun handleRfidsAccountingObjects(
            rfids: List<String>,
            accountingObjects: List<AccountingObjectDomain>,
            getState: () -> DocumentCreateStore.State
        ) {
            if (canEditDocument(getState())) {
                val newAccountingObjects = documentCreateInteractor.handleNewAccountingObjectRfids(
                    accountingObjects = accountingObjects,
                    handledAccountingObjectRfids = rfids
                )
                dispatch(Result.AccountingObjects(newAccountingObjects))
                tryChangeReturnExploiting(getState)
            }
        }

        private suspend fun changeParams(
            params: List<ParamDomain>
        ) {
            val filters = params.ifEmpty {
                selectParamsInteractor.getInitialDocumentParams(
                    getParams(documentType = documentCreateArguments.document.documentType.name)
                )
            }

            dispatch(Result.Params(filters))
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
            dispatch(Result.IsDocumentChangeEnabled(canEditDocument(state)))
        }

        private fun showParams(params: List<ParamDomain>, param: ParamDomain) {
            publish(
                DocumentCreateStore.Label.ShowParamSteps(
                    currentFilter = param,
                    allParams = params
                )
            )
        }


        private suspend fun onChooseAccountingObjectClicked(getState: () -> DocumentCreateStore.State) {
            publish(
                DocumentCreateStore.Label.ShowAccountingObjects(
                    documentCreateInteractor.getExploitingFilterIfDocumentReturn(
                        documentType = getState().document.documentType,
                        params = getState().params
                    ),
                    documentCreateInteractor.getAccountingObjectIds(getState().accountingObjects)
                )
            )
        }

        private suspend fun onChooseReserveClicked(getState: () -> DocumentCreateStore.State) {
            val filters = when (getState().document.documentType) {
                DocumentTypeDomain.GIVE -> listOf(
                    ParamDomain(
                        id = UNMARKED_TMC_ID,
                        type = ManualType.RECEPTION_CATEGORY,
                    ),
                    CheckBoxParamDomain(
                        isChecked = true,
                        manualType = ManualType.CHECKBOX_HIDE_ZERO_RESERVES
                    )
                )

                DocumentTypeDomain.RELOCATION -> listOf(
                    CheckBoxParamDomain(
                        isChecked = true,
                        manualType = ManualType.CHECKBOX_HIDE_ZERO_RESERVES
                    )
                )

                else -> listOf()
            }
            publish(
                DocumentCreateStore.Label.ShowReserves(
                    filters,
                    documentCreateInteractor.getReservesIds(getState().reserves)
                )
            )
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            dispatch(Result.ConfirmDialogType(AlertType.NONE))
            publish(DocumentCreateStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class DialogListItem(val listItem: List<String>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
        data class Document(val document: DocumentDomain) : Result()
        data class Params(val params: List<ParamDomain>) : Result()
        data class SelectPage(val page: Int) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
        data class Reserves(val reserves: List<ReservesDomain>) : Result()
        data class ConfirmDialogType(val type: AlertType) : Result()
        data class DialogLoading(val isLoading: Boolean) : Result()
        data class CanUpdate(val canUpdate: Boolean) : Result()
        data class CanCreate(val canCreate: Boolean) : Result()
        data class CanDelete(val canDelete: Boolean) : Result()
        data class ReadingMode(val readingModeTab: ReadingModeTab) : Result()
        data class IsDocumentChangeEnabled(val isDocumentChangeEnabled: Boolean) : Result()
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
                is Result.ConfirmDialogType -> copy(confirmDialogType = result.type)
                is Result.CanUpdate -> copy(canUpdate = result.canUpdate)
                is Result.CanCreate -> copy(canCreate = result.canCreate)
                is Result.ReadingMode -> copy(readingModeTab = result.readingModeTab)
                is Result.CanDelete -> copy(canDelete = result.canDelete)
                is Result.DialogListItem -> copy(dialogListItem = result.listItem)
                is Result.DialogLoading -> copy(dialogLoading = result.isLoading)
                is Result.IsDocumentChangeEnabled -> copy(isDocumentChangeEnabled = result.isDocumentChangeEnabled)
            }
    }

    companion object {
        private const val UNMARKED_TMC_ID = "UNMARKED_TMC"
    }
}