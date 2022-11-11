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
import com.itrocket.union.identify.domain.IdentifyInteractor
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.readingMode.presentation.view.toReadingModeTab
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.ui.listAction.DialogActionType
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class IdentifyStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val identifyInteractor: IdentifyInteractor,
    private val serviceEntryManager: ServiceEntryManager
) {
    fun create(): IdentifyStore =
        object : IdentifyStore,
            Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> by storeFactory.create(
                name = "IdentifyStore",
                initialState = IdentifyStore.State(readingModeTab = serviceEntryManager.currentMode.toReadingModeTab()),
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
                is IdentifyStore.Intent.OnNewAccountingObjectRfidHandled -> handleRfidAccountingObjects(
                    intent.rfids,
                    getState().accountingObjects
                )
                is IdentifyStore.Intent.OnNewAccountingObjectBarcodeHandled -> {
                    handleBarcodeAccountingObjects(
                        intent.barcode,
                        getState().accountingObjects,
                        getState().readingModeTab
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
            }
        }

        private suspend fun onWriteOffClicked(accountingObjects: List<AccountingObjectDomain>) {
            dispatch(Result.LoadingDialogActionType(DialogActionType.WRITE_OFF))
            catchException {
                dispatch(
                    Result.AccountingObjects(
                        identifyInteractor.writeOffAccountingObjects(
                            accountingObjects
                        )
                    )
                )
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
                    handleBarcodeAccountingObjects(
                        barcode = readingModeResult.scanData,
                        accountingObjects = getState().accountingObjects,
                        readingModeTab = getState().readingModeTab
                    )
                }
            }
        }

        private suspend fun handleRfidAccountingObjects(
            rfids: List<String>,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            val newAccountingObjects = identifyInteractor.handleNewAccountingObjectRfids(
                accountingObjects = accountingObjects,
                handledAccountingObjectRfids = rfids
            )
            dispatch(Result.AccountingObjects(newAccountingObjects))
        }

        private suspend fun handleBarcodeAccountingObjects(
            barcode: String,
            accountingObjects: List<AccountingObjectDomain>,
            readingModeTab: ReadingModeTab
        ) {
            val newAccountingObjects = identifyInteractor.handleNewAccountingObjectBarcode(
                accountingObjects = accountingObjects,
                barcode = barcode,
                isSerialNumber = readingModeTab == ReadingModeTab.SN
            )
            dispatch(Result.AccountingObjects(newAccountingObjects))
        }
    }

    private sealed class Result {
        data class DialogType(val dialogType: AlertType) : Result()
        data class Loading(val isLoading: Boolean) : Result()
        data class ReadingMode(val readingModeTab: ReadingModeTab) : Result()
        data class Reserves(val reserves: List<ReservesDomain>) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) :
            Result()

        data class LoadingDialogActionType(val dialogActionType: DialogActionType?) : Result()
    }

    private object ReducerImpl : Reducer<IdentifyStore.State, Result> {
        override fun IdentifyStore.State.reduce(result: Result): IdentifyStore.State =
            when (result) {
                is Result.Loading -> copy(isIdentifyLoading = result.isLoading)
                is Result.AccountingObjects -> copy(accountingObjects = result.accountingObjects)
                is Result.Reserves -> copy(reserves = result.reserves)
                is Result.ReadingMode -> copy(readingModeTab = result.readingModeTab)
                is Result.DialogType -> copy(dialogType = result.dialogType)
                is Result.LoadingDialogActionType -> copy(loadingDialogAction = result.dialogActionType)
            }
    }
}