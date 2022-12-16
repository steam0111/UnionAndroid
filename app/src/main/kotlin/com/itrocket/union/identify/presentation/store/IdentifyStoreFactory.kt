package com.itrocket.union.identify.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.identify.domain.IdentifyInteractor
import com.itrocket.union.identify.domain.IdentifyInteractor.Companion.ACCOUNTING_OBJECT_PAGE
import com.itrocket.union.identify.domain.IdentifyInteractor.Companion.NOMENCLATURE_RESERVE_PAGE
import com.itrocket.union.identify.domain.NomenclatureReserveDomain
import com.itrocket.union.moduleSettings.domain.ModuleSettingsInteractor
import com.itrocket.union.readingMode.domain.ReadingModeInteractor
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.readingMode.presentation.view.toReadingMode
import com.itrocket.union.ui.listAction.DialogActionType
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class IdentifyStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val identifyInteractor: IdentifyInteractor,
    private val serviceEntryManager: ServiceEntryManager,
    private val readingModeInteractor: ReadingModeInteractor,
    private val moduleSettingsInteractor: ModuleSettingsInteractor,
    private val unionPermissionsInteractor: UnionPermissionsInteractor,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): IdentifyStore =
        object : IdentifyStore,
            Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> by storeFactory.create(
                name = "IdentifyStore",
                initialState = IdentifyStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<IdentifyStore.Intent, Unit, IdentifyStore.State, Result, IdentifyStore.Label> =
        IdentifyExecutor()

    private inner class IdentifyExecutor :
        BaseExecutor<IdentifyStore.Intent, Unit, IdentifyStore.State, Result, IdentifyStore.Label>(
            context = coreDispatchers.ui
        ) {

        override suspend fun executeAction(
            action: Unit,
            getState: () -> IdentifyStore.State
        ) {
            dispatch(
                Result.CanUpdateAccountingObjects(
                    unionPermissionsInteractor.canUpdate(
                        UnionPermission.ACCOUNTING_OBJECT
                    )
                )
            )
            dispatch(Result.ReadingMode(moduleSettingsInteractor.getDefaultReadingMode(isForceUpdate = true)))
        }

        override suspend fun executeIntent(
            intent: IdentifyStore.Intent,
            getState: () -> IdentifyStore.State
        ) {
            when (intent) {
                IdentifyStore.Intent.OnBackClicked -> publish(IdentifyStore.Label.GoBack)
                IdentifyStore.Intent.OnDropClicked -> {
                    dispatch(Result.AccountingObjects(listOf()))
                }
                IdentifyStore.Intent.OnReadingModeClicked -> {
                    publish(IdentifyStore.Label.ShowReadingMode)
                }
                is IdentifyStore.Intent.OnItemClicked -> {
                    publish(
                        IdentifyStore.Label.ShowDetail(
                            intent.accountingObject,
                            getState().accountingObjects
                        )
                    )
                }
                is IdentifyStore.Intent.OnNewRfidHandled -> handleRfid(
                    rfids = intent.rfids,
                    getState = getState
                )
                is IdentifyStore.Intent.OnNewBarcodeHandled -> {
                    handleBarcode(
                        barcode = intent.barcode,
                        getState = getState
                    )
                }
                is IdentifyStore.Intent.OnAccountingObjectSelected -> {
                    dispatch(
                        Result.AccountingObjects(
                            identifyInteractor.addAccountingObject(
                                accountingObjects = getState().accountingObjects,
                                accountingObject = intent.accountingObject
                            )
                        )
                    )
                }
                is IdentifyStore.Intent.OnDeleteFromSelectActionWithValuesBottomMenu -> {
                    dispatch(Result.AccountingObjects(intent.accountingObjects))
                }
                is IdentifyStore.Intent.OnReadingModeTabChanged -> dispatch(
                    Result.ReadingMode(
                        intent.readingModeTab
                    )
                )
                is IdentifyStore.Intent.OnManualInput -> onManualInput(
                    readingModeResult = intent.readingModeResult,
                    getState = getState
                )
                IdentifyStore.Intent.OnPlusClicked -> onPlusClicked()
                IdentifyStore.Intent.OnListActionDialogDismissed -> onListActionDialogDismissed()
                is IdentifyStore.Intent.OnListActionDialogClicked -> when (intent.dialogActionType) {
                    DialogActionType.WRITE_OFF -> onWriteOffClicked(accountingObjects = getState().accountingObjects)
                }
                is IdentifyStore.Intent.OnAccountingObjectClosed -> onAccountingObjectClosed(
                    getState = getState
                )
                is IdentifyStore.Intent.OnErrorHandled -> handleError(intent.throwable)
                is IdentifyStore.Intent.OnNomenclatureReserveClicked -> onNomenclatureReserveClicked(
                    intent.nomenclatureReserveDomain
                )
                is IdentifyStore.Intent.OnSelectPage -> dispatch(Result.Page(intent.selectedPage))
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(IdentifyStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        private fun onNomenclatureReserveClicked(nomenclatureReserveDomain: NomenclatureReserveDomain) {
            publish(IdentifyStore.Label.ShowNomenclature(nomenclatureReserveDomain.nomenclatureId))
        }

        private suspend fun onAccountingObjectClosed(
            getState: () -> IdentifyStore.State
        ) {
            readingModeInteractor.changeScanMode(getState().readingModeTab.toReadingMode())
        }

        private suspend fun onWriteOffClicked(accountingObjects: List<AccountingObjectDomain>) {
            dispatch(Result.LoadingDialogActionType(DialogActionType.WRITE_OFF))
            catchException {
                identifyInteractor.writeOffAccountingObjects(accountingObjects)
            }
            dispatch(Result.LoadingDialogActionType(null))
            dispatch(Result.DialogType(AlertType.NONE))
        }

        private fun onListActionDialogDismissed() {
            dispatch(Result.DialogType(AlertType.NONE))
        }

        private fun onPlusClicked() {
            dispatch(Result.DialogType(AlertType.LIST_ACTION))
        }

        private suspend fun onManualInput(
            readingModeResult: ReadingModeResult,
            getState: () -> IdentifyStore.State
        ) {
            when (readingModeResult.readingModeTab) {
                ReadingModeTab.RFID -> {
                    //no-op
                }
                ReadingModeTab.BARCODE, ReadingModeTab.SN -> {
                    handleBarcode(
                        barcode = readingModeResult.scanData,
                        getState = getState
                    )
                }
            }
        }

        private suspend fun handleRfid(
            rfids: List<String>,
            getState: () -> IdentifyStore.State
        ) {
            when (getState().selectedPage) {
                ACCOUNTING_OBJECT_PAGE -> {
                    val newAccountingObjects = identifyInteractor.handleNewAccountingObjectRfids(
                        accountingObjects = getState().accountingObjects,
                        handledAccountingObjectRfids = rfids
                    )
                    dispatch(Result.AccountingObjects(newAccountingObjects))
                }
                NOMENCLATURE_RESERVE_PAGE -> {
                    val oldRfids = getState().nomenclatureRfids
                    val nomenclatureReservesRfid =
                        identifyInteractor.handleNewNomenclatureReserveRfids(
                            nomenclatureReserves = getState().nomenclatureReserves,
                            rfids = rfids,
                            oldsRfids = getState().nomenclatureRfids
                        )
                    dispatch(Result.NomenclatureReserves(nomenclatureReservesRfid.newNomenclatureReserves))
                    dispatch(Result.NomenclatureRfids(oldRfids + nomenclatureReservesRfid.newRfids))
                }
            }
        }

        private suspend fun handleBarcode(
            barcode: String,
            getState: () -> IdentifyStore.State,
        ) {
            when (getState().selectedPage) {
                ACCOUNTING_OBJECT_PAGE -> {
                    val newAccountingObjects = identifyInteractor.handleNewAccountingObjectBarcode(
                        accountingObjects = getState().accountingObjects,
                        barcode = barcode,
                        isSerialNumber = getState().readingModeTab == ReadingModeTab.SN
                    )
                    dispatch(Result.AccountingObjects(newAccountingObjects))
                }
                NOMENCLATURE_RESERVE_PAGE -> {
                    val newNomenclatureReserves =
                        identifyInteractor.handleNewNomenclatureReserveBarcode(
                            barcode = barcode,
                            nomenclatureReserves = getState().nomenclatureReserves
                        )
                    dispatch(Result.NomenclatureReserves(newNomenclatureReserves))
                }
            }
        }
    }

    private sealed class Result {
        data class CanUpdateAccountingObjects(val canUpdateAccountingObjects: Boolean) :
            Result()

        data class Page(val page: Int) : Result()
        data class DialogType(val dialogType: AlertType) : Result()
        data class Loading(val isLoading: Boolean) : Result()
        data class ReadingMode(val readingModeTab: ReadingModeTab) : Result()
        data class NomenclatureReserves(val nomenclatureReserves: List<NomenclatureReserveDomain>) :
            Result()

        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) :
            Result()

        data class LoadingDialogActionType(val dialogActionType: DialogActionType?) : Result()
        data class NomenclatureRfids(val rfids: List<String>) : Result()
    }

    private object ReducerImpl : Reducer<IdentifyStore.State, Result> {
        override fun IdentifyStore.State.reduce(result: Result): IdentifyStore.State =
            when (result) {
                is Result.Loading -> copy(isIdentifyLoading = result.isLoading)
                is Result.AccountingObjects -> copy(accountingObjects = result.accountingObjects)
                is Result.NomenclatureReserves -> copy(nomenclatureReserves = result.nomenclatureReserves)
                is Result.ReadingMode -> copy(readingModeTab = result.readingModeTab)
                is Result.DialogType -> copy(dialogType = result.dialogType)
                is Result.LoadingDialogActionType -> copy(loadingDialogAction = result.dialogActionType)
                is Result.CanUpdateAccountingObjects -> copy(canUpdateAccountingObjects = result.canUpdateAccountingObjects)
                is Result.Page -> copy(selectedPage = result.page)
                is Result.NomenclatureRfids -> copy(nomenclatureRfids = result.rfids)
            }
    }
}