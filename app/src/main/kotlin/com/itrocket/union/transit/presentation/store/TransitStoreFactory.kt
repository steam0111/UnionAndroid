package com.itrocket.union.transit.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documentCreate.domain.DocumentCreateInteractor
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStoreFactory
import com.itrocket.union.documents.data.mapper.getTransitParams
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.nfcReader.presentation.store.NfcReaderResult
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.readingMode.presentation.view.toReadingModeTab
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.selectParams.domain.SelectParamsInteractor
import com.itrocket.union.transit.domain.TransitAccountingObjectManager
import com.itrocket.union.transit.domain.TransitInteractor
import com.itrocket.union.transit.domain.TransitRemainsManager
import com.itrocket.union.transit.domain.TransitTypeDomain
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class TransitStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val transitInteractor: TransitInteractor,
    private val transitArguments: TransitArguments,
    private val errorInteractor: ErrorInteractor,
    private val filterInteractor: FilterInteractor,
    private val documentCreateInteractor: DocumentCreateInteractor,
    private val selectParamsInteractor: SelectParamsInteractor,
    private val transitAccountingObjectManager: TransitAccountingObjectManager,
    private val transitReservesManager: TransitRemainsManager,
    private val unionPermissionsInteractor: UnionPermissionsInteractor,
    private val serviceEntryManager: ServiceEntryManager,
) {
    fun create(): TransitStore =
        object : TransitStore,
            Store<TransitStore.Intent, TransitStore.State, TransitStore.Label> by storeFactory.create(
                name = "TransitStore",
                initialState = TransitStore.State(
                    transit = transitArguments.transit,
                    accountingObjects = transitArguments.transit.accountingObjects,
                    params = transitArguments.transit.params,
                    reserves = transitArguments.transit.reserves,
                    readingModeTab = serviceEntryManager.currentMode.toReadingModeTab()
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<TransitStore.Intent, Unit, TransitStore.State, Result, TransitStore.Label> =
        TransitExecutor()

    private inner class TransitExecutor :
        BaseExecutor<TransitStore.Intent, Unit, TransitStore.State, Result, TransitStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> TransitStore.State
        ) {
            dispatch(Result.Loading(true))
            dispatch(Result.CanUpdate(unionPermissionsInteractor.canUpdate(UnionPermission.TRANSIT)))
            dispatch(Result.CanCreate(unionPermissionsInteractor.canCreate(UnionPermission.TRANSIT)))
            catchException {
                val transitArgument = transitArguments.transit
                val transit = if (transitArgument.isDocumentExists) {
                    transitArgument.id?.let { transitInteractor.getTransitById(it) }
                } else {
                    null
                }

                transit?.let { dispatch(Result.Transit(it)) }
                dispatch(Result.AccountingObjects(transit?.accountingObjects ?: listOf()))
                dispatch(Result.Reserves(transit?.reserves ?: listOf()))
                val params = transit?.params
                    ?: selectParamsInteractor.getInitialDocumentParams(
                        getTransitParams()
                    )
                dispatch(Result.Params(params))
            }
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: TransitStore.Intent,
            getState: () -> TransitStore.State
        ) {
            when (intent) {
                TransitStore.Intent.OnBackClicked -> publish(TransitStore.Label.GoBack)
                TransitStore.Intent.OnChooseAccountingObjectClicked -> onChooseAccountingObjectClicked(
                    getState
                )
                TransitStore.Intent.OnChooseReserveClicked -> onChooseReserveClicked(getState)
                TransitStore.Intent.OnDropClicked -> {
                    changeParams(getState().transit.params)
                    dispatch(Result.AccountingObjects(getState().transit.accountingObjects))
                }
                is TransitStore.Intent.OnParamClicked -> {
                    if (!getState().transit.isStatusCompleted) {
                        showParams(
                            params = getState().params,
                            param = intent.param
                        )
                    }
                }
                is TransitStore.Intent.OnParamCrossClicked -> {
                    val params = documentCreateInteractor.clearParam(
                        list = getState().params,
                        param = intent.param,
                        documentType = getState().transit.documentType
                    )
                    changeParams(params = params)
                }
                is TransitStore.Intent.OnParamsChanged -> {
                    val params = documentCreateInteractor.changeParams(
                        oldParams = getState().params,
                        newParams = intent.params,
                        documentType = getState().transit.documentType
                    )
                    changeParams(params = params)
                }
                TransitStore.Intent.OnSaveClicked -> dispatch(
                    Result.ConfirmDialogType(
                        AlertType.SAVE
                    )
                )
                is TransitStore.Intent.OnSelectPage -> dispatch(Result.SelectPage(intent.selectedPage))
                TransitStore.Intent.OnSettingsClicked -> publish(TransitStore.Label.ShowReadingMode)
                is TransitStore.Intent.OnAccountingObjectSelected -> {
                    dispatch(
                        Result.AccountingObjects(
                            documentCreateInteractor.addAccountingObject(
                                accountingObjects = getState().accountingObjects,
                                accountingObject = intent.accountingObjectDomain
                            )
                        )
                    )
                }
                is TransitStore.Intent.OnNewAccountingObjectBarcodeHandled -> handleBarcodeAccountingObjects(
                    barcode = intent.barcode,
                    accountingObjects = getState().accountingObjects,
                    readingModeTab = getState().readingModeTab,
                    getState = getState
                )
                is TransitStore.Intent.OnNewAccountingObjectRfidHandled -> handleRfidsAccountingObjects(
                    rfids = intent.rfids,
                    accountingObjects = getState().accountingObjects,
                    getState = getState
                )
                is TransitStore.Intent.OnReserveSelected -> onReserveSelected(
                    reserves = getState().reserves,
                    newReserve = intent.reserve
                )
                TransitStore.Intent.OnCompleteClicked -> dispatch(
                    Result.ConfirmDialogType(
                        AlertType.CONDUCT
                    )
                )
                is TransitStore.Intent.OnReserveCountSelected -> dispatch(
                    Result.Reserves(
                        documentCreateInteractor.updateReserveCount(
                            reserves = getState().reserves,
                            id = intent.result.id,
                            count = intent.result.count
                        )
                    )
                )
                is TransitStore.Intent.OnReserveClicked -> publish(
                    TransitStore.Label.ShowSelectCount(
                        id = intent.reserve.id,
                        count = intent.reserve.itemsCount
                    )
                )
                is TransitStore.Intent.OnDismissConfirmDialog -> {
                    dispatch(Result.ConfirmDialogType(AlertType.NONE))
                }
                is TransitStore.Intent.OnConfirmActionClick -> {
                    handleOnConfirmActionClick(getState)
                }
                is TransitStore.Intent.OnNfcReaderClose -> {
                    onNfcReaderClose(intent.nfcReaderResult)
                }
                is TransitStore.Intent.OnReadingModeTabChanged -> dispatch(Result.ReadingMode(intent.readingModeTab))
                is TransitStore.Intent.OnManualInput -> onManualInput(
                    readingModeResult = intent.readingModeResult,
                    getState = getState
                )
                is TransitStore.Intent.OnErrorHandled -> handleError(intent.throwable)
            }
        }

        private fun canEditTransit(state: TransitStore.State): Boolean {
            return with(state) {
                (transit.isDocumentExists && canUpdate || !transit.isDocumentExists && canCreate) && transit.documentStatus != DocumentStatus.COMPLETED
            }
        }

        private suspend fun onManualInput(
            readingModeResult: ReadingModeResult,
            getState: () -> TransitStore.State
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
                        transitInteractor.getTransitById(requireNotNull(nfcReaderResult.documentId))
                    dispatch(Result.Transit(document))
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
                TransitStore.Label.ShowSelectCount(
                    id = newReserve.id,
                    count = newReserve.itemsCount
                )
            )
        }

        private suspend fun handleOnConfirmActionClick(getState: () -> TransitStore.State) {
            if (getState().confirmDialogType == AlertType.SAVE) {
                saveDocument(getState)
            } else if (getState().confirmDialogType == AlertType.CONDUCT) {
                onConductClicked(getState())
            }
            dispatch(Result.ConfirmDialogType(AlertType.NONE))
        }

        private suspend fun onConductClicked(state: TransitStore.State) {
            val conductPermission = UnionPermission.ALL_DOCUMENTS
            if (unionPermissionsInteractor.canConductDocument(conductPermission)) {
                conductDocument(state)
            } else {
                publish(
                    TransitStore.Label.ShowNfcReader(
                        documentCreateInteractor.completeDocument(
                            document = state.transit,
                            accountingObjects = state.accountingObjects,
                            reserves = state.reserves,
                            params = state.params
                        )
                    )
                )
            }
        }

        private suspend fun handleBarcodeAccountingObjects(
            barcode: String,
            accountingObjects: List<AccountingObjectDomain>,
            readingModeTab: ReadingModeTab,
            getState: () -> TransitStore.State
        ) {
            if (canEditTransit(getState())) {
                val newAccountingObjects =
                    documentCreateInteractor.handleNewAccountingObjectBarcode(
                        accountingObjects = accountingObjects,
                        barcode = barcode,
                        isSerialNumber = readingModeTab == ReadingModeTab.SN
                    )
                dispatch(Result.AccountingObjects(newAccountingObjects))
            }
        }

        private suspend fun handleRfidsAccountingObjects(
            rfids: List<String>,
            accountingObjects: List<AccountingObjectDomain>,
            getState: () -> TransitStore.State
        ) {
            if (canEditTransit(getState())) {
                val newAccountingObjects = documentCreateInteractor.handleNewAccountingObjectRfids(
                    accountingObjects = accountingObjects,
                    handledAccountingObjectRfids = rfids
                )
                dispatch(Result.AccountingObjects(newAccountingObjects))
            }
        }

        private fun changeParams(
            params: List<ParamDomain>
        ) {
            dispatch(Result.Params(params))
        }

        private suspend fun conductDocument(state: TransitStore.State) {
            val transitId = transitInteractor.createOrUpdateDocument(
                transit = state.transit,
                accountingObjects = state.accountingObjects,
                params = state.params,
                reserves = state.reserves,
                status = DocumentStatus.COMPLETED,
                transitTypeDomain = state.transit.transitType ?: TransitTypeDomain.TRANSIT_SENDING
            )
            transitAccountingObjectManager.changeAccountingObjectsAfterConduct(
                transitTypeDomain = state.transit.transitType ?: TransitTypeDomain.TRANSIT_SENDING,
                accountingObjects = state.accountingObjects,
                params = state.params
            )
            transitReservesManager.conductReserve(
                reserves = state.reserves,
                params = state.params,
                transitTypeDomain = state.transit.transitType ?: TransitTypeDomain.TRANSIT_SENDING
            )
            dispatch(Result.Transit(state.transit.copy(documentStatus = DocumentStatus.COMPLETED)))
            if (state.transit.transitType != TransitTypeDomain.TRANSIT_RECEPTION) {
                createReceptionTransit(transitId)
            }
        }

        private suspend fun createReceptionTransit(transitId: String) {
            val transit = transitInteractor.getTransitById(transitId)
            transitInteractor.createOrUpdateDocument(
                transitTypeDomain = TransitTypeDomain.TRANSIT_RECEPTION,
                accountingObjects = transit.accountingObjects,
                reserves = transit.reserves,
                params = transit.params,
                status = DocumentStatus.CREATED,
                transit = transit.copy(id = null)
            )
        }


        private suspend fun saveDocument(getState: () -> TransitStore.State) {
            transitInteractor.createOrUpdateDocument(
                accountingObjects = getState().accountingObjects,
                transit = getState().transit,
                params = getState().params,
                reserves = getState().reserves,
                status = getState().transit.documentStatus,
                transitTypeDomain = getState().transit.transitType
                    ?: TransitTypeDomain.TRANSIT_RECEPTION
            )
        }

        private fun showParams(params: List<ParamDomain>, param: ParamDomain) {
            TransitStore.Label.ShowParamSteps(
                currentFilter = param,
                allParams = params
            )
        }

        private suspend fun onChooseAccountingObjectClicked(getState: () -> TransitStore.State) {
            publish(
                TransitStore.Label.ShowAccountingObjects(
                    listOf(),
                    documentCreateInteractor.getAccountingObjectIds(getState().accountingObjects)
                )
            )
        }

        private suspend fun onChooseReserveClicked(getState: () -> TransitStore.State) {
            publish(
                TransitStore.Label.ShowReserves(
                    listOf(),
                    documentCreateInteractor.getReservesIds(getState().reserves)
                )
            )
        }

        override fun handleError(throwable: Throwable) {
            publish(TransitStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Transit(val transit: DocumentDomain) : Result()
        data class Params(val params: List<ParamDomain>) : Result()
        data class SelectPage(val page: Int) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
        data class Reserves(val reserves: List<ReservesDomain>) : Result()
        data class ConfirmDialogType(val type: AlertType) : Result()
        data class CanUpdate(val canUpdate: Boolean) : Result()
        data class CanCreate(val canCreate: Boolean) : Result()
        data class ReadingMode(val readingModeTab: ReadingModeTab) : Result()
    }

    private object ReducerImpl : Reducer<TransitStore.State, Result> {
        override fun TransitStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.AccountingObjects -> copy(accountingObjects = result.accountingObjects)
                is Result.Params -> copy(params = result.params)
                is Result.Reserves -> copy(reserves = result.reserves)
                is Result.SelectPage -> copy(selectedPage = result.page)
                is Result.Transit -> copy(transit = result.transit)
                is Result.ConfirmDialogType -> copy(confirmDialogType = result.type)
                is Result.CanUpdate -> copy(canUpdate = result.canUpdate)
                is Result.CanCreate -> copy(canCreate = result.canCreate)
                is Result.ReadingMode -> copy(readingModeTab = result.readingModeTab)
            }
    }
}