package com.itrocket.union.identify.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documentCreate.domain.DocumentCreateInteractor
import com.itrocket.union.identify.domain.IdentifyInteractor
import com.itrocket.union.reserves.domain.entity.ReservesDomain

class IdentifyStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val identifyInteractor: IdentifyInteractor
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
                    intent.rfid,
                    getState().accountingObjects
                )
                is IdentifyStore.Intent.OnNewAccountingObjectBarcodeHandled -> {
                    handleBarcodeAccountingObjects(
                        intent.barcode,
                        getState().accountingObjects
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
            }
        }

        private suspend fun handleRfidAccountingObjects(
            rfid: String,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            val newAccountingObjects = identifyInteractor.handleNewAccountingObjectRfids(
                accountingObjects = accountingObjects,
                handledAccountingObjectRfid = rfid
            )
            dispatch(Result.AccountingObjects(newAccountingObjects))
        }

        private suspend fun handleBarcodeAccountingObjects(
            barcode: String,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            val newAccountingObjects = identifyInteractor.handleNewAccountingObjectBarcode(
                accountingObjects = accountingObjects,
                barcode = barcode
            )
            dispatch(Result.AccountingObjects(newAccountingObjects))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Reserves(val reserves: List<ReservesDomain>) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) :
            Result()
    }

    private object ReducerImpl : Reducer<IdentifyStore.State, Result> {
        override fun IdentifyStore.State.reduce(result: Result): IdentifyStore.State =
            when (result) {
                is Result.Loading -> copy(isIdentifyLoading = result.isLoading)
                is Result.AccountingObjects -> copy(accountingObjects = result.accountingObjects)
                is Result.Reserves -> copy(reserves = result.reserves)
            }
    }
}