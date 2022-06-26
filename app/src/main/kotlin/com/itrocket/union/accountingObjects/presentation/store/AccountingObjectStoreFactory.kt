package com.itrocket.union.accountingObjects.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.utils.ifBlankOrNull

class AccountingObjectStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val accountingObjectInteractor: AccountingObjectInteractor,
    private val accountingObjectArguments: AccountingObjectArguments?,
    private val errorInteractor: ErrorInteractor
) {

    private var params: List<ParamDomain>? = null

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
            getAccountingObjects(accountingObjectArguments?.params.orEmpty())
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
                AccountingObjectStore.Intent.OnBackClicked -> onBackClicked(
                    getState().isShowSearch,
                    params.orEmpty()
                )
                AccountingObjectStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                AccountingObjectStore.Intent.OnFilterClicked -> publish(
                    AccountingObjectStore.Label.ShowFilter(
                        params ?: accountingObjectInteractor.getFilters()
                    )
                )
                is AccountingObjectStore.Intent.OnFilterResult -> {
                    params = intent.params
                    getAccountingObjects(params.orEmpty(), getState().searchText)
                }
                is AccountingObjectStore.Intent.OnItemClicked -> onItemClick(intent.item)
                is AccountingObjectStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    getAccountingObjects(params.orEmpty(), intent.searchText)
                }
            }
        }


        private suspend fun onBackClicked(isShowSearch: Boolean, params: List<ParamDomain>) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                getAccountingObjects(params, "")
            } else {
                publish(AccountingObjectStore.Label.GoBack())
            }
        }

        private suspend fun getAccountingObjects(
            params: List<ParamDomain>,
            searchText: String = ""
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.AccountingObjects(
                        accountingObjectInteractor.getAccountingObjects(
                            params = params,
                            searchQuery = searchText,
                            selectedAccountingObjectIds = accountingObjectArguments?.selectedAccountingObjectIds.orEmpty()
                        )
                    )
                )
                dispatch(Result.Loading(false))
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
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<AccountingObjectStore.State, Result> {
        override fun AccountingObjectStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.AccountingObjects -> copy(accountingObjects = result.accountingObjects)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
            }
    }
}