package com.itrocket.union.accountingObjectDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.domain.AccountingObjectDetailInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.changeScanData.data.mapper.toChangeScanType
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.readingMode.presentation.view.toReadingModeTab
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import ru.interid.scannerclient_impl.platform.entry.ReadingMode
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class AccountingObjectDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: AccountingObjectDetailInteractor,
    private val accountingObjectDetailArguments: AccountingObjectDetailArguments,
    private val errorInteractor: ErrorInteractor,
    private val serviceEntryManager: ServiceEntryManager,
    private val unionPermissionsInteractor: UnionPermissionsInteractor
) {
    fun create(): AccountingObjectDetailStore =
        object : AccountingObjectDetailStore,
            Store<AccountingObjectDetailStore.Intent, AccountingObjectDetailStore.State, AccountingObjectDetailStore.Label> by storeFactory.create(
                name = "AccountingObjectDetailStore",
                initialState = AccountingObjectDetailStore.State(
                    accountingObjectDomain = accountingObjectDetailArguments.argument,
                    readingMode = serviceEntryManager.currentMode.toReadingModeTab()
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<AccountingObjectDetailStore.Intent, Unit, AccountingObjectDetailStore.State, Result, AccountingObjectDetailStore.Label> =
        AccountingObjectDetailExecutor()

    private inner class AccountingObjectDetailExecutor :
        BaseExecutor<AccountingObjectDetailStore.Intent, Unit, AccountingObjectDetailStore.State, Result, AccountingObjectDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AccountingObjectDetailStore.State
        ) {
            dispatch(Result.CanUpdate(unionPermissionsInteractor.canUpdate(UnionPermission.ACCOUNTING_OBJECT)))
            listenAccountingObject()
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(AccountingObjectDetailStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        override suspend fun executeIntent(
            intent: AccountingObjectDetailStore.Intent,
            getState: () -> AccountingObjectDetailStore.State
        ) {
            when (intent) {
                AccountingObjectDetailStore.Intent.OnBackClicked -> publish(
                    AccountingObjectDetailStore.Label.GoBack(AccountingObjectDetailResult(getState().accountingObjectDomain))
                )
                AccountingObjectDetailStore.Intent.OnReadingModeClicked -> {
                    publish(AccountingObjectDetailStore.Label.ShowReadingMode(getState().readingMode))
                }
                AccountingObjectDetailStore.Intent.OnDocumentAddClicked -> {
                    //no-op
                }
                AccountingObjectDetailStore.Intent.OnDocumentSearchClicked -> {
                    //no-op
                }
                is AccountingObjectDetailStore.Intent.OnPageSelected -> dispatch(
                    Result.NewPage(intent.selectedPage)
                )
                is AccountingObjectDetailStore.Intent.OnScanHandled -> onScanHandled(
                    getState = getState,
                    scanData = intent.scanData
                )
                is AccountingObjectDetailStore.Intent.OnReadingModeTabChanged -> dispatch(
                    Result.ReadingMode(
                        intent.readingModeTab
                    )
                )
                AccountingObjectDetailStore.Intent.OnMarkingClosed -> publish(
                    AccountingObjectDetailStore.Label.ChangeSubscribeScanData(true)
                )
                is AccountingObjectDetailStore.Intent.OnManualInput -> onManualInput(
                    readingModeResult = intent.readingModeResult,
                    getState = getState
                )
                AccountingObjectDetailStore.Intent.OnGenerateRfidClicked -> interactor.generateRfid(
                    getState().accountingObjectDomain
                )
                AccountingObjectDetailStore.Intent.OnWriteEpcClicked -> dispatch(
                    Result.DialogType(
                        AlertType.WRITE_EPC
                    )
                )
                AccountingObjectDetailStore.Intent.OnDismissed -> onDismissed()
                AccountingObjectDetailStore.Intent.OnTriggerPressed -> onTriggerPressed(getState)
                AccountingObjectDetailStore.Intent.OnTriggerReleased -> onTriggerRelease()
                is AccountingObjectDetailStore.Intent.OnWriteEpcError -> onWriteEpcError(intent.error)
                is AccountingObjectDetailStore.Intent.OnWriteEpcHandled -> onWriteEpcHandled()
                AccountingObjectDetailStore.Intent.OnWriteOffClicked -> onWriteOffClicked(getState().accountingObjectDomain)
                AccountingObjectDetailStore.Intent.OnRemoveBarcodeClicked -> onRemoveBarcodeClicked(
                    getState().accountingObjectDomain
                )
                AccountingObjectDetailStore.Intent.OnRemoveRfidClicked -> onRemoveRfidClicked(
                    getState().accountingObjectDomain
                )
            }
        }

        private suspend fun onWriteOffClicked(accountingObjectDomain: AccountingObjectDomain) {
            catchException {
                interactor.writeOffAccountingObject(accountingObjectDomain)
            }
        }

        private suspend fun onRemoveBarcodeClicked(accountingObjectDomain: AccountingObjectDomain) {
            catchException {
                interactor.removeBarcode(accountingObjectDomain)
            }
        }

        private suspend fun onRemoveRfidClicked(accountingObjectDomain: AccountingObjectDomain) {
            catchException {
                interactor.removeRfid(accountingObjectDomain)
            }
        }

        private fun onDismissed() {
            dispatch(Result.DialogType(AlertType.NONE))
            dispatch(Result.RfidError(""))
        }

        private fun onWriteEpcError(error: String) {
            dispatch(Result.RfidError(error))
        }

        private fun onWriteEpcHandled() {
            dispatch(Result.RfidError(""))
            dispatch(Result.DialogType(AlertType.NONE))
        }

        private fun onTriggerPressed(getState: () -> AccountingObjectDetailStore.State) {
            when {
                serviceEntryManager.currentMode == ReadingMode.RFID && getState().dialogType == AlertType.WRITE_EPC -> {
                    serviceEntryManager.writeEpcTag(
                        getState().accountingObjectDomain.rfidValue.orEmpty()
                    )
                }
                serviceEntryManager.currentMode == ReadingMode.RFID -> serviceEntryManager.epcInventory()
                else -> serviceEntryManager.startBarcodeScan()
            }
        }

        private fun onTriggerRelease() {
            if (serviceEntryManager.currentMode == ReadingMode.RFID) {
                serviceEntryManager.stopRfidOperation()
            } else {
                serviceEntryManager.stopBarcodeScan()
            }
        }

        private fun onManualInput(
            readingModeResult: ReadingModeResult,
            getState: () -> AccountingObjectDetailStore.State
        ) {
            dispatch(Result.ReadingMode(readingModeResult.readingModeTab))
            onScanHandled(getState = getState, scanData = readingModeResult.scanData)
        }

        private fun onScanHandled(
            getState: () -> AccountingObjectDetailStore.State,
            scanData: String
        ) {
            val canChangeScanData =
                getState().canUpdate && getState().dialogType != AlertType.WRITE_EPC
            if (canChangeScanData) {
                publish(AccountingObjectDetailStore.Label.ChangeSubscribeScanData(false))
                publish(
                    AccountingObjectDetailStore.Label.ShowChangeScanData(
                        entityId = getState().accountingObjectDomain.id,
                        scanValue = when (getState().readingMode) {
                            ReadingModeTab.RFID -> getState().accountingObjectDomain.rfidValue
                            ReadingModeTab.BARCODE -> getState().accountingObjectDomain.barcodeValue
                            ReadingModeTab.SN -> getState().accountingObjectDomain.factoryNumber
                        },
                        changeScanType = getState().readingMode.toChangeScanType(),
                        newScanValue = scanData
                    )
                )
            }
        }

        private suspend fun listenAccountingObject() {
            catchException {
                dispatch(Result.Loading(true))
                interactor.getAccountingObjectFlow(id = accountingObjectDetailArguments.argument.id)
                    .catch {
                        handleError(it)
                    }.collect {
                        dispatch(Result.Loading(false))
                        dispatch(Result.AccountingObject(it))
                    }
            }
        }
    }

    private sealed class Result {
        data class RfidError(val rfidError: String) : Result()
        data class NewPage(val page: Int) : Result()
        data class Loading(val isLoading: Boolean) : Result()
        data class AccountingObject(val obj: AccountingObjectDomain) : Result()
        data class ReadingMode(val readingModeTab: ReadingModeTab) : Result()
        data class CanUpdate(val canUpdate: Boolean) : Result()
        data class DialogType(val dialogType: AlertType) : Result()
    }

    private object ReducerImpl : Reducer<AccountingObjectDetailStore.State, Result> {
        override fun AccountingObjectDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.NewPage -> copy(selectedPage = result.page)
                is Result.AccountingObject -> copy(accountingObjectDomain = result.obj)
                is Result.ReadingMode -> copy(readingMode = result.readingModeTab)
                is Result.CanUpdate -> copy(canUpdate = result.canUpdate)
                is Result.DialogType -> copy(dialogType = result.dialogType)
                is Result.RfidError -> copy(rfidError = result.rfidError)
            }
    }
}