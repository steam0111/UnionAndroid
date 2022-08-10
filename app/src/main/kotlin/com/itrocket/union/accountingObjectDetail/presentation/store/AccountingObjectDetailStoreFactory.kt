package com.itrocket.union.accountingObjectDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.domain.AccountingObjectDetailInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.changeScanData.data.mapper.toChangeScanType
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class AccountingObjectDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: AccountingObjectDetailInteractor,
    private val accountingObjectDetailArguments: AccountingObjectDetailArguments
) {
    fun create(): AccountingObjectDetailStore =
        object : AccountingObjectDetailStore,
            Store<AccountingObjectDetailStore.Intent, AccountingObjectDetailStore.State, AccountingObjectDetailStore.Label> by storeFactory.create(
                name = "AccountingObjectDetailStore",
                initialState = AccountingObjectDetailStore.State(
                    accountingObjectDomain = accountingObjectDetailArguments.argument
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
            listenAccountingObject()
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(AccountingObjectDetailStore.Label.Error(throwable.message.orEmpty()))
        }

        override suspend fun executeIntent(
            intent: AccountingObjectDetailStore.Intent,
            getState: () -> AccountingObjectDetailStore.State
        ) {
            when (intent) {
                AccountingObjectDetailStore.Intent.OnBackClicked -> publish(
                    AccountingObjectDetailStore.Label.GoBack
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
                is AccountingObjectDetailStore.Intent.OnMarkingClicked -> {
                    publish(
                        AccountingObjectDetailStore.Label.ShowChangeScanData(
                            entityId = getState().accountingObjectDomain.id,
                            scanValue = when (intent.readingModeTab) {
                                ReadingModeTab.RFID -> getState().accountingObjectDomain.rfidValue
                                ReadingModeTab.BARCODE -> getState().accountingObjectDomain.barcodeValue
                                ReadingModeTab.SN -> getState().accountingObjectDomain.factoryNumber
                            },
                            changeScanType = intent.readingModeTab.toChangeScanType()
                        )
                    )
                }
                is AccountingObjectDetailStore.Intent.OnReadingModeTabChanged -> dispatch(
                    Result.ReadingMode(
                        intent.readingModeTab
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
        data class NewPage(val page: Int) : Result()
        data class Loading(val isLoading: Boolean) : Result()
        data class AccountingObject(val obj: AccountingObjectDomain) : Result()
        data class ReadingMode(val readingModeTab: ReadingModeTab) : Result()
    }

    private object ReducerImpl : Reducer<AccountingObjectDetailStore.State, Result> {
        override fun AccountingObjectDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.NewPage -> copy(selectedPage = result.page)
                is Result.AccountingObject -> copy(accountingObjectDomain = result.obj)
                is Result.ReadingMode -> copy(readingMode = result.readingModeTab)
            }
    }
}