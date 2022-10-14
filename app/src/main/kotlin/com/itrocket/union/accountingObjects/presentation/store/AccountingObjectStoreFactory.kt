package com.itrocket.union.accountingObjects.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import com.itrocket.utils.paging.Paginator

class AccountingObjectStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val accountingObjectInteractor: AccountingObjectInteractor,
    private val accountingObjectArguments: AccountingObjectArguments?,
    private val searchManager: SearchManager,
    private val errorInteractor: ErrorInteractor
) {

    fun create(): AccountingObjectStore =
        object : AccountingObjectStore,
            Store<AccountingObjectStore.Intent, AccountingObjectStore.State, AccountingObjectStore.Label> by storeFactory.create(
                name = "AccountingObjectStore",
                initialState = AccountingObjectStore.State(params = accountingObjectArguments?.params.orEmpty()),
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

        private val paginator = Paginator<AccountingObjectDomain>(
            onError = {
                handleError(it)
            },
            onLoadUpdate = {
                dispatch(Result.Loading(it))
            },
            onSuccess = {
                dispatch(Result.AccountingObjects(it))
            }
        )

        override suspend fun executeAction(
            action: Unit,
            getState: () -> AccountingObjectStore.State
        ) {
            val filters = getState().params.ifEmpty {
                accountingObjectInteractor.getFilters(accountingObjectArguments?.isFromDocument == true)
            }
            dispatch(Result.Params(filters))
            searchManager.listenSearch {
                reset()
                paginator.onLoadNext {
                    getAccountingObjects(
                        params = getState().params,
                        searchText = getState().searchText,
                        offset = it
                    )
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(AccountingObjectStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        override suspend fun executeIntent(
            intent: AccountingObjectStore.Intent,
            getState: () -> AccountingObjectStore.State
        ) {
            when (intent) {
                AccountingObjectStore.Intent.OnBackClicked -> onBackClicked(
                    getState().isShowSearch
                )
                AccountingObjectStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                AccountingObjectStore.Intent.OnFilterClicked -> {
                    publish(AccountingObjectStore.Label.ShowFilter(getState().params))
                }
                is AccountingObjectStore.Intent.OnFilterResult -> {
                    dispatch(Result.Params(intent.params))
                    reset()
                    paginator.onLoadNext {
                        getAccountingObjects(
                            params = getState().params,
                            searchText = getState().searchText,
                            offset = it
                        )
                    }
                }
                is AccountingObjectStore.Intent.OnItemClicked -> onItemClick(intent.item)
                is AccountingObjectStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
                AccountingObjectStore.Intent.OnLoadNext -> paginator.onLoadNext {
                    getAccountingObjects(
                        params = getState().params,
                        searchText = getState().searchText,
                        offset = it
                    )
                }
            }
        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.emit("")
            } else {
                publish(AccountingObjectStore.Label.GoBack())
            }
        }

        private suspend fun getAccountingObjects(
            params: List<ParamDomain>,
            searchText: String = "",
            offset: Long = 0
        ) = runCatching {
            val accountingObjects = accountingObjectInteractor.getAccountingObjects(
                locationManualType = ManualType.LOCATION,
                params = params,
                searchQuery = searchText,
                selectedAccountingObjectIds = accountingObjectArguments?.selectedAccountingObjectIds.orEmpty(),
                offset = offset,
                limit = Paginator.PAGE_SIZE
            )
            if (accountingObjects.isEmpty()) {
                dispatch(Result.IsListEndReached(true))
            }
            accountingObjects
        }

        private suspend fun reset() {
            paginator.reset()
            dispatch(Result.IsListEndReached(false))
            dispatch(Result.AccountingObjects(listOf()))
        }

        private fun onItemClick(item: AccountingObjectDomain) {
            if (accountingObjectArguments?.isFromDocument == true) {
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
        data class Params(val params: List<ParamDomain>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class IsListEndReached(val isListEndReached: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<AccountingObjectStore.State, Result> {
        override fun AccountingObjectStore.State.reduce(result: Result) =
            when (result) {
                is Result.Params -> copy(params = result.params)
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.AccountingObjects -> copy(accountingObjects = result.accountingObjects)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.IsListEndReached -> copy(isListEndReached = result.isListEndReached)
            }
    }
}