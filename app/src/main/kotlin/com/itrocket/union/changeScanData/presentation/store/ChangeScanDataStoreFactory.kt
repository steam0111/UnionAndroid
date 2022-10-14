package com.itrocket.union.changeScanData.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.changeScanData.domain.ChangeScanDataInteractor
import com.itrocket.union.changeScanData.domain.entity.ChangeScanType
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.search.TextFieldManager
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.flow.collect

class ChangeScanDataStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val changeScanDataInteractor: ChangeScanDataInteractor,
    private val changeScanDataArguments: ChangeScanDataArguments,
    private val textFieldManager: TextFieldManager,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): ChangeScanDataStore =
        object : ChangeScanDataStore,
            Store<ChangeScanDataStore.Intent, ChangeScanDataStore.State, ChangeScanDataStore.Label> by storeFactory.create(
                name = "ChangeScanDataStore",
                initialState = ChangeScanDataStore.State(
                    currentScanValue = changeScanDataArguments.scanValue,
                    changeScanType = changeScanDataArguments.changeScanType,
                    newScanValue = changeScanDataArguments.newScanValue
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ChangeScanDataStore.Intent, Unit, ChangeScanDataStore.State, Result, ChangeScanDataStore.Label> =
        ChangeScanDataExecutor()

    private inner class ChangeScanDataExecutor :
        BaseExecutor<ChangeScanDataStore.Intent, Unit, ChangeScanDataStore.State, Result, ChangeScanDataStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ChangeScanDataStore.State
        ) {
            onScanningValueChanged(
                scanningValue = getState().newScanValue,
                changeScanType = getState().changeScanType
            )
            textFieldManager.listenSearch().collect {
                onScanningValueChanged(
                    scanningValue = it,
                    changeScanType = getState().changeScanType
                )
            }
        }

        override suspend fun executeIntent(
            intent: ChangeScanDataStore.Intent,
            getState: () -> ChangeScanDataStore.State
        ) {
            when (intent) {
                ChangeScanDataStore.Intent.OnBackClicked -> publish(ChangeScanDataStore.Label.GoBack)
                ChangeScanDataStore.Intent.OnApplyClicked -> {
                    catchException {
                        changeScanDataInteractor.changeAccountingObjectScanningValue(
                            entityId = changeScanDataArguments.entityId,
                            scanningValue = getState().newScanValue,
                            changeScanType = getState().changeScanType
                        )
                        publish(ChangeScanDataStore.Label.GoBack)
                    }
                }
                ChangeScanDataStore.Intent.OnCancelClicked -> publish(ChangeScanDataStore.Label.GoBack)
                is ChangeScanDataStore.Intent.OnPowerChanged -> {
                    //no-op
                }
                ChangeScanDataStore.Intent.OnPowerClicked -> publish(ChangeScanDataStore.Label.ReaderPower)
                is ChangeScanDataStore.Intent.OnScanning -> {
                    dispatch(Result.ScanningValue(intent.scanningValue))
                    onScanningValueChanged(
                        intent.scanningValue,
                        getState().changeScanType
                    )
                }
                is ChangeScanDataStore.Intent.OnScanDataChanged -> {
                    dispatch(Result.ScanningValue(intent.scanData))
                    textFieldManager.emit(intent.scanData)
                }
            }
        }

        private suspend fun onScanningValueChanged(
            scanningValue: String,
            changeScanType: ChangeScanType
        ) {
            val isScanDataExist = changeScanDataInteractor.isScanDataExist(
                changeScanType = changeScanType,
                scanData = scanningValue
            )
            if (isScanDataExist) {
                dispatch(Result.IsScanDataExistError(true))
                dispatch(Result.IsApplyEnabled(false))
            } else {
                dispatch(Result.IsScanDataExistError(false))
                dispatch(Result.IsApplyEnabled(true))
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(ChangeScanDataStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class ScanningValue(val scanningValue: String) : Result()
        data class IsApplyEnabled(val isApplyEnabled: Boolean) : Result()
        data class IsScanDataExistError(val isScanDataExistError: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<ChangeScanDataStore.State, Result> {
        override fun ChangeScanDataStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.ScanningValue -> copy(newScanValue = result.scanningValue)
                is Result.IsApplyEnabled -> copy(isApplyEnabled = result.isApplyEnabled)
                is Result.IsScanDataExistError -> copy(isScanDataExistError = result.isScanDataExistError)
            }
    }
}