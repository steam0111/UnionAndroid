package com.itrocket.union.dataCollect.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.dataCollect.domain.DataCollectInteractor

class DataCollectStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val dataCollectInteractor: DataCollectInteractor
) {
    fun create(): DataCollectStore =
        object : DataCollectStore,
            Store<DataCollectStore.Intent, DataCollectStore.State, DataCollectStore.Label> by storeFactory.create(
                name = "DataCollectStore",
                initialState = DataCollectStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<DataCollectStore.Intent, Unit, DataCollectStore.State, Result, DataCollectStore.Label> =
        DataCollectExecutor()

    private inner class DataCollectExecutor :
        BaseExecutor<DataCollectStore.Intent, Unit, DataCollectStore.State, Result, DataCollectStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> DataCollectStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: DataCollectStore.Intent,
            getState: () -> DataCollectStore.State
        ) {
            when (intent) {
                DataCollectStore.Intent.OnBackClicked -> publish(DataCollectStore.Label.GoBack)
                DataCollectStore.Intent.OnDropClicked -> {
                    dispatch(Result.ScanningObjects(listOf()))
                }
                DataCollectStore.Intent.OnReadingModeClicked -> {
                    publish(DataCollectStore.Label.ShowReadingMode)
                }
                is DataCollectStore.Intent.OnNewAccountingObjectRfidHandled -> {
                    val newScanningList: List<String> = dataCollectInteractor.rfidsToNewList(
                        intent.rfids,
                        getState().scanningObjects
                    )
                    dispatch(Result.ScanningObjects(newScanningList))
                }
                is DataCollectStore.Intent.OnNewAccountingObjectBarcodeHandled -> {
                    val newScanningList: List<String> = dataCollectInteractor.barcodeToNewList(
                        intent.barcode,
                        getState().scanningObjects
                    )
                    dispatch(Result.ScanningObjects(newScanningList))
                }
            }
        }
    }

    private sealed class Result {
        data class ScanningObjects(
            val scanningObjects: List<String>
        ) : Result()
    }

    private object ReducerImpl : Reducer<DataCollectStore.State, Result> {
        override fun DataCollectStore.State.reduce(result: Result): DataCollectStore.State =
            when (result) {
                is Result.ScanningObjects -> copy(scanningObjects = result.scanningObjects)
            }
    }
}