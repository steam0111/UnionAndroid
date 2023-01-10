package com.itrocket.union.reserveDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.sgtin.IncorrectBarcodeException
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailStore
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.moduleSettings.domain.ModuleSettingsInteractor
import com.itrocket.union.readingMode.domain.ReadingModeInteractor
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserveDetail.domain.ReserveDetailInteractor
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.terminalRemainsNumerator.domain.TerminalRemainsNumeratorDomain
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission
import ru.interid.scannerclient_impl.platform.entry.ReadingMode
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ReserveDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val reserveDetailInteractor: ReserveDetailInteractor,
    private val reserveDetailArguments: ReserveDetailArguments,
    private val errorInteractor: ErrorInteractor,
    private val moduleSettingsInteractor: ModuleSettingsInteractor,
    private val unionPermissionsInteractor: UnionPermissionsInteractor,
    private val serviceEntryManager: ServiceEntryManager,
    private val readingModeInteractor: ReadingModeInteractor
) {
    fun create(): ReserveDetailStore =
        object : ReserveDetailStore,
            Store<ReserveDetailStore.Intent, ReserveDetailStore.State, ReserveDetailStore.Label> by storeFactory.create(
                name = "ReserveDetailStore",
                initialState = ReserveDetailStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ReserveDetailStore.Intent, Unit, ReserveDetailStore.State, Result, ReserveDetailStore.Label> =
        ReserveDetailExecutor()

    private inner class ReserveDetailExecutor :
        BaseExecutor<ReserveDetailStore.Intent, Unit, ReserveDetailStore.State, Result, ReserveDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ReserveDetailStore.State
        ) {
            dispatch(Result.ReadingMode(moduleSettingsInteractor.getDefaultReadingMode(isForceUpdate = true)))
            dispatch(Result.CanUpdate(unionPermissionsInteractor.canUpdate(UnionPermission.REMAINS)))
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.Reserve(
                        reserveDetailInteractor.getReserveById(
                            reserveDetailArguments.id
                        )
                    )
                )
                dispatch(Result.ShowButtons(true))
                dispatch(Result.Loading(false))
            }
        }

        override suspend fun executeIntent(
            intent: ReserveDetailStore.Intent,
            getState: () -> ReserveDetailStore.State
        ) {
            when (intent) {
                ReserveDetailStore.Intent.OnBackClicked -> publish(
                    ReserveDetailStore.Label.GoBack
                )
                ReserveDetailStore.Intent.OnReadingModeClicked -> {
                    publish(ReserveDetailStore.Label.ShowReadingMode(getState().readingMode))
                }
                ReserveDetailStore.Intent.OnDocumentAddClicked -> {
                    //no-op
                }
                ReserveDetailStore.Intent.OnDocumentSearchClicked -> {
                    //no-op
                }
                is ReserveDetailStore.Intent.OnReadingModeTabChanged -> dispatch(
                    Result.ReadingMode(
                        intent.readingModeTab
                    )
                )
                ReserveDetailStore.Intent.OnMarkingClicked -> onMarkingClicked(getState = getState)
                ReserveDetailStore.Intent.OnTriggerPressed -> onTriggerPressed(getState = getState)
                ReserveDetailStore.Intent.OnTriggerReleased -> onTriggerRelease()
                is ReserveDetailStore.Intent.OnWriteEpcError -> onWriteEpcError()
                is ReserveDetailStore.Intent.OnWriteEpcHandled -> onWriteEpcHandled(getState = getState)
                is ReserveDetailStore.Intent.OnErrorHandled -> handleError(throwable = intent.error)
                ReserveDetailStore.Intent.OnDismissed -> onDismissed()
                ReserveDetailStore.Intent.OnLabelTypeEditClicked -> onLabelTypeEditClicked()
                is ReserveDetailStore.Intent.OnLabelTypeSelected -> onLabelTypeSelected(
                    getState = getState,
                    labelTypeId = intent.labelTypeId
                )
            }
        }

        private fun onLabelTypeEditClicked() {
            publish(ReserveDetailStore.Label.ShowLabelTypes)
        }

        private suspend fun onLabelTypeSelected(
            getState: () -> ReserveDetailStore.State,
            labelTypeId: String
        ) {
            getState().reserve?.let {
                dispatch(
                    Result.Reserve(
                        reserveDetailInteractor.updateLabelType(
                            reserve = it,
                            labelTypeId = labelTypeId
                        )
                    )
                )
            }
        }

        private fun onDismissed() {
            dispatch(Result.DialogType(AlertType.NONE))
            dispatch(Result.RfidError(""))
            dispatch(Result.Rfid(null))
            dispatch(Result.TerminalRemainsNumerator(null))
        }


        private suspend fun onWriteEpcHandled(getState: () -> ReserveDetailStore.State) {
            catchException {
                val terminalRemainsNumerator = requireNotNull(getState().terminalRemainsNumerator)
                dispatch(Result.RfidError(""))
                dispatch(Result.DialogType(AlertType.NONE))
                dispatch(Result.Rfid(null))
                reserveDetailInteractor.updateActualNumber(
                    remainsId = terminalRemainsNumerator.remainsId,
                    actualNumber = terminalRemainsNumerator.actualNumber
                )
                publish(
                    ReserveDetailStore.Label.ShowToast(
                        message = errorInteractor.getMessageByResId(R.string.accounting_object_detail_write_epc_success),
                        backgroundColor = R.color.green
                    )
                )
                dispatch(Result.TerminalRemainsNumerator(null))
            }
        }

        private fun onWriteEpcError() {
            dispatch(Result.RfidError(errorInteractor.getMessageByResId(R.string.accounting_object_detail_write_epc_error)))
        }

        private fun onTriggerPressed(getState: () -> ReserveDetailStore.State) {
            val rfid = getState().rfid
            when {
                serviceEntryManager.currentMode == ReadingMode.RFID && rfid != null -> {
                    serviceEntryManager.writeEpcTag(rfid)
                }
            }
        }

        private fun onTriggerRelease() {
            if (serviceEntryManager.currentMode == ReadingMode.RFID) {
                serviceEntryManager.stopRfidOperation()
            } else {
                serviceEntryManager.stopBarcodeScan()
            }
        }

        private suspend fun onMarkingClicked(getState: () -> ReserveDetailStore.State) {
            dispatch(Result.Loading(true))
            catchException {
                val reserve = getState().reserve
                val barcode = reserve?.barcodeValue
                val terminalNumerator = reserveDetailInteractor.getTerminalRemainsNumeratorById(
                    remainsId = requireNotNull(reserve?.id)
                )

                val rfid = reserveDetailInteractor.generateSgtinRfid(
                    barcode = barcode,
                    actualNumber = terminalNumerator.actualNumber,
                    terminalPrefix = terminalNumerator.terminalPrefix
                )

                readingModeInteractor.changeScanMode(ReadingMode.RFID)

                dispatch(Result.ReadingMode(ReadingModeTab.RFID))
                dispatch(Result.Rfid(rfid))
                dispatch(Result.TerminalRemainsNumerator(terminalNumerator))
                dispatch(Result.DialogType(dialogType = AlertType.WRITE_EPC))
            }
            dispatch(Result.Loading(false))
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(
                ReserveDetailStore.Label.Error(
                    when (throwable) {
                        is IncorrectBarcodeException -> errorInteractor.getMessageByResId(R.string.common_formatter_error)
                        else -> errorInteractor.getTextMessage(throwable)
                    }
                )
            )
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class ReadingMode(val readingModeTab: ReadingModeTab) : Result()
        data class Reserve(val reserve: ReservesDomain) : Result()
        data class CanUpdate(val canUpdate: Boolean) : Result()
        data class ShowButtons(val showButtons: Boolean) : Result()
        data class Rfid(val rfid: String?) : Result()
        data class DialogType(val dialogType: AlertType) : Result()
        data class RfidError(val rfidError: String) : Result()
        data class TerminalRemainsNumerator(val terminalRemainsNumerator: TerminalRemainsNumeratorDomain?) :
            Result()
    }

    private object ReducerImpl : Reducer<ReserveDetailStore.State, Result> {
        override fun ReserveDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Reserve -> copy(reserve = result.reserve)
                is Result.ReadingMode -> copy(readingMode = result.readingModeTab)
                is Result.CanUpdate -> copy(canUpdate = result.canUpdate)
                is Result.ShowButtons -> copy(showButtons = result.showButtons)
                is Result.DialogType -> copy(dialogType = result.dialogType)
                is Result.Rfid -> copy(rfid = result.rfid)
                is Result.RfidError -> copy(rfidError = result.rfidError)
                is Result.TerminalRemainsNumerator -> copy(terminalRemainsNumerator = result.terminalRemainsNumerator)
            }
    }
}