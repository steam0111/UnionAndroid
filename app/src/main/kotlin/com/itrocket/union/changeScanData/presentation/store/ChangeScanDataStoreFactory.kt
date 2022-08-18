package com.itrocket.union.changeScanData.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.changeScanData.domain.ChangeScanDataInteractor
import com.itrocket.union.changeScanData.domain.entity.ChangeScanDataDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor

class ChangeScanDataStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val changeScanDataInteractor: ChangeScanDataInteractor,
    private val changeScanDataArguments: ChangeScanDataArguments
) {
    fun create(): ChangeScanDataStore =
        object : ChangeScanDataStore,
            Store<ChangeScanDataStore.Intent, ChangeScanDataStore.State, ChangeScanDataStore.Label> by storeFactory.create(
                name = "ChangeScanDataStore",
                initialState = ChangeScanDataStore.State(
                    currentScanValue = changeScanDataArguments.scanValue,
                    changeScanType = changeScanDataArguments.changeScanType
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
                ChangeScanDataStore.Intent.OnPowerClicked -> {
                    //no-op
                }
                is ChangeScanDataStore.Intent.OnScanning -> {
                    val scanningValue = intent.scanningValue
                    dispatch(Result.ScanningValue(scanningValue))
                    dispatch(Result.IsApplyEnabled(scanningValue.isNotBlank()))
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(ChangeScanDataStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class ScanningValue(val scanningValue: String) : Result()
        data class IsApplyEnabled(val isApplyEnabled: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<ChangeScanDataStore.State, Result> {
        override fun ChangeScanDataStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.ScanningValue -> copy(newScanValue = result.scanningValue)
                is Result.IsApplyEnabled -> copy(isApplyEnabled = result.isApplyEnabled)
            }
    }
}