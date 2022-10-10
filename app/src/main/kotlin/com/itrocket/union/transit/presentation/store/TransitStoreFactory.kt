package com.itrocket.union.transit.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.union.transit.domain.TransitInteractor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documentCreate.domain.DocumentCreateInteractor
import com.itrocket.union.documentCreate.presentation.view.DocumentConfirmAlertType
import com.itrocket.union.documents.data.mapper.getTransitParams
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.Params
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.nfcReader.presentation.store.NfcReaderResult
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.selectParams.domain.SelectParamsInteractor
import com.itrocket.union.transit.domain.TransitAccountingObjectManager
import com.itrocket.union.transit.domain.TransitRemainsManager
import com.itrocket.union.transit.domain.TransitTypeDomain
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission

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
    private val unionPermissionsInteractor: UnionPermissionsInteractor
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
            dispatch(Result.IsCanUpdate(unionPermissionsInteractor.canCreate(UnionPermission.TRANSIT)))
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
                        getState().params,
                        intent.param
                    )
                    changeParams(params = params)
                }
                is TransitStore.Intent.OnParamsChanged -> {
                    val params = documentCreateInteractor.changeParams(
                        getState().params,
                        intent.params
                    )
                    changeParams(params = params)
                }
                TransitStore.Intent.OnSaveClicked -> dispatch(
                    Result.ConfirmDialogType(
                        DocumentConfirmAlertType.SAVE
                    )
                )
                is TransitStore.Intent.OnSelectPage -> dispatch(Result.SelectPage(intent.selectedPage))
                TransitStore.Intent.OnSettingsClicked -> publish(TransitStore.Label.ShowReadingMode)
                is TransitStore.Intent.OnLocationChanged -> {
                    val params = documentCreateInteractor.changeLocation(
                        getState().params,
                        intent.location.location
                    )
                    changeParams(params = params)
                }
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
                    intent.barcode,
                    getState().accountingObjects
                )
                is TransitStore.Intent.OnNewAccountingObjectRfidHandled -> handleRfidsAccountingObjects(
                    intent.rfid,
                    getState().accountingObjects
                )
                is TransitStore.Intent.OnReserveSelected -> onReserveSelected(
                    reserves = getState().reserves,
                    newReserve = intent.reserve
                )
                TransitStore.Intent.OnCompleteClicked -> dispatch(
                    Result.ConfirmDialogType(
                        DocumentConfirmAlertType.CONDUCT
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
                is TransitStore.Intent.OnStructuralChanged -> {
                    val params = documentCreateInteractor.changeStructural(
                        getState().params,
                        intent.structural.structural
                    )
                    changeParams(
                        params = params
                    )
                }
                is TransitStore.Intent.OnDismissConfirmDialog -> {
                    dispatch(Result.ConfirmDialogType(DocumentConfirmAlertType.NONE))
                }
                is TransitStore.Intent.OnConfirmActionClick -> {
                    handleOnConfirmActionClick(getState)
                }
                is TransitStore.Intent.OnNfcReaderClose -> {
                    onNfcReaderClose(intent.nfcReaderResult)
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
            if (getState().confirmDialogType == DocumentConfirmAlertType.SAVE) {
                saveDocument(getState)
            } else if (getState().confirmDialogType == DocumentConfirmAlertType.CONDUCT) {
                onConductClicked(getState())
            }
            dispatch(Result.ConfirmDialogType(DocumentConfirmAlertType.NONE))
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
            when (param.type) {
                ManualType.LOCATION, ManualType.LOCATION_FROM,
                ManualType.RELOCATION_LOCATION_TO, ManualType.LOCATION_TO, ManualType.TRANSIT -> publish(
                    TransitStore.Label.ShowLocation(param as LocationParamDomain)
                )
                ManualType.STRUCTURAL_TO, ManualType.STRUCTURAL_FROM -> publish(
                    TransitStore.Label.ShowStructural(
                        param as StructuralParamDomain
                    )
                )
                else -> {
                    val defaultTypeParams =
                        filterInteractor.getDefaultTypeParams(Params(params))
                    val currentStep = defaultTypeParams.indexOf(param) + 1
                    publish(
                        TransitStore.Label.ShowParamSteps(
                            currentStep = currentStep,
                            params = defaultTypeParams,
                            allParams = params
                        )
                    )
                }
            }
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
            publish(TransitStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Transit(val transit: DocumentDomain) : Result()
        data class Params(val params: List<ParamDomain>) : Result()
        data class SelectPage(val page: Int) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
        data class Reserves(val reserves: List<ReservesDomain>) : Result()
        data class ConfirmDialogType(val type: DocumentConfirmAlertType) : Result()
        data class IsCanUpdate(val isCanUpdate: Boolean) : Result()
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
                is Result.IsCanUpdate -> copy(isCanUpdate = result.isCanUpdate)
            }
    }
}