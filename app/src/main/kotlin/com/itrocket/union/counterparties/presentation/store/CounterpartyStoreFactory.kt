package com.itrocket.union.counterparties.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.counterparties.domain.CounterpartyInteractor
import com.itrocket.union.counterparties.domain.entity.CounterpartyDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import com.itrocket.utils.paging.Paginator
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class CounterpartyStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val counterpartyInteractor: CounterpartyInteractor,
    private val searchManager: SearchManager,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): CounterpartyStore =
        object : CounterpartyStore,
            Store<CounterpartyStore.Intent, CounterpartyStore.State, CounterpartyStore.Label> by storeFactory.create(
                name = "CounterpartyStore",
                initialState = CounterpartyStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<CounterpartyStore.Intent, Unit, CounterpartyStore.State, Result, CounterpartyStore.Label> =
        CounterpartyExecutor()

    private inner class CounterpartyExecutor :
        BaseExecutor<CounterpartyStore.Intent, Unit, CounterpartyStore.State, Result, CounterpartyStore.Label>(
            context = coreDispatchers.ui
        ) {

        private val paginator = Paginator<CounterpartyDomain>(
            onError = {
                handleError(it)
            },
            onLoadUpdate = {
                dispatch(Result.Loading(it))
            },
            onSuccess = {
                dispatch(Result.Counterparties(it))
            },
            onEndReached = {
                dispatch(Result.IsListEndReached(true))
            }
        )

        override suspend fun executeAction(
            action: Unit,
            getState: () -> CounterpartyStore.State
        ) {
            searchManager.listenSearch {
                reset()
                paginator.onLoadNext {
                    getCounterparties(getState().searchText, offset = it)
                }
            }
        }

        override suspend fun executeIntent(
            intent: CounterpartyStore.Intent,
            getState: () -> CounterpartyStore.State
        ) {
            when (intent) {
                CounterpartyStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                is CounterpartyStore.Intent.OnCounterpartyClicked -> {
                    publish(CounterpartyStore.Label.ShowDetail(intent.id))
                }
                CounterpartyStore.Intent.OnFilterClicked -> {
                }
                CounterpartyStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is CounterpartyStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
                is CounterpartyStore.Intent.OnLoadNext -> paginator.onLoadNext {
                    getCounterparties(
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
                publish(CounterpartyStore.Label.GoBack)
            }
        }

        private suspend fun getCounterparties(searchText: String = "", offset: Long = 0) =
            runCatching {
                val counterparties = counterpartyInteractor.getCounterparties(
                    searchText,
                    offset = offset,
                    limit = Paginator.PAGE_SIZE
                )
                counterparties
            }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(CounterpartyStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        private suspend fun reset() {
            paginator.reset()
            dispatch(Result.IsListEndReached(false))
            dispatch(Result.Counterparties(listOf()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Counterparties(val counterparties: List<CounterpartyDomain>) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class IsListEndReached(val isListEndReached: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<CounterpartyStore.State, Result> {
        override fun CounterpartyStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Counterparties -> copy(counterparties = result.counterparties)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.IsListEndReached -> copy(isListEndReached = result.isListEndReached)
            }
    }
}