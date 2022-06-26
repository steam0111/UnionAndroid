package com.itrocket.union.accountingObjects.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.utils.ifBlankOrNull

class AccountingObjectStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val accountingObjectInteractor: AccountingObjectInteractor,
    private val filterInteractor: FilterInteractor,
    private val accountingObjectArguments: AccountingObjectArguments?,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): AccountingObjectStore =
        object : AccountingObjectStore,
            Store<AccountingObjectStore.Intent, AccountingObjectStore.State, AccountingObjectStore.Label> by storeFactory.create(
                name = "AccountingObjectStore",
                initialState = AccountingObjectStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<AccountingObjectStore.Intent, Unit, AccountingObjectStore.State, Result, AccountingObjectStore.Label> =
        AccountingObjectExecutor()

    private inner class AccountingObjectExecutor :
        BaseExecutor<AccountingObjectStore.Intent, Unit, AccountingObjectStore.State, Result, AccountingObjectStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AccountingObjectStore.State
        ) {
            dispatch(Result.Loading(true))
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.AccountingObjects(
                        accountingObjectInteractor.getAccountingObjects(
                            params = accountingObjectArguments?.params.orEmpty(),
                            selectedAccountingObjectIds = accountingObjectArguments?.selectedAccountingObjectIds.orEmpty()
                        )
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(AccountingObjectStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }

        override suspend fun executeIntent(
            intent: AccountingObjectStore.Intent,
            getState: () -> AccountingObjectStore.State
        ) {
            when (intent) {
                AccountingObjectStore.Intent.OnBackClicked -> publish(AccountingObjectStore.Label.GoBack())
                AccountingObjectStore.Intent.OnSearchClicked -> publish(
                    AccountingObjectStore.Label.ShowSearch
                )
                AccountingObjectStore.Intent.OnFilterClicked -> publish(
                    AccountingObjectStore.Label.ShowFilter(filterInteractor.getFilters())
                )
                is AccountingObjectStore.Intent.OnItemClicked -> onItemClick(intent.item)
            }
        }

        private fun onItemClick(item: AccountingObjectDomain) {
            if (accountingObjectArguments?.params?.isNotEmpty() == true) {
                publish(AccountingObjectStore.Label.GoBack(AccountingObjectResult(item)))
            } else {
                publish(
                    AccountingObjectStore.Label.ShowDetail(
                        item
                    )
                )
            }
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
    }

    private object ReducerImpl : Reducer<AccountingObjectStore.State, Result> {
        override fun AccountingObjectStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.AccountingObjects -> copy(accountingObjects = result.accountingObjects)
            }
    }
}