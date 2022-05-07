package com.itrocket.union.accountingObjectDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.accountingObjectDetail.domain.AccountingObjectDetailInteractor
import com.itrocket.core.base.CoreDispatchers

class AccountingObjectDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val accountingObjectDetailInteractor: AccountingObjectDetailInteractor,
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
        SuspendExecutor<AccountingObjectDetailStore.Intent, Unit, AccountingObjectDetailStore.State, Result, AccountingObjectDetailStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AccountingObjectDetailStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: AccountingObjectDetailStore.Intent,
            getState: () -> AccountingObjectDetailStore.State
        ) {
            when (intent) {
                AccountingObjectDetailStore.Intent.OnBackClicked -> publish(
                    AccountingObjectDetailStore.Label.GoBack
                )
                is AccountingObjectDetailStore.Intent.OnCheckedFullCharacteristics -> dispatch(
                    Result.CheckedFullCharacteristics(intent.isChecked)
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
            }
        }
    }

    private sealed class Result {
        data class NewPage(val page: Int) : Result()
        data class Loading(val isLoading: Boolean) : Result()
        data class CheckedFullCharacteristics(val isChecked: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<AccountingObjectDetailStore.State, Result> {
        override fun AccountingObjectDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.CheckedFullCharacteristics -> copy(isFullCharacteristicChecked = result.isChecked)
                is Result.NewPage -> copy(selectedPage = result.page)
            }
    }
}