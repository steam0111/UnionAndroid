package com.itrocket.union.producer.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.producer.domain.ProducerInteractor
import com.itrocket.union.producer.domain.entity.ProducerDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import com.itrocket.utils.paging.Paginator

class ProducerStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val producerInteractor: ProducerInteractor,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager
) {
    fun create(): ProducerStore =
        object : ProducerStore,
            Store<ProducerStore.Intent, ProducerStore.State, ProducerStore.Label> by storeFactory.create(
                name = "ProducerStore",
                initialState = ProducerStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ProducerStore.Intent, Unit, ProducerStore.State, Result, ProducerStore.Label> =
        ProducerExecutor()

    private inner class ProducerExecutor :
        BaseExecutor<ProducerStore.Intent, Unit, ProducerStore.State, Result, ProducerStore.Label>(
            context = coreDispatchers.ui
        ) {

        private val paginator = Paginator<ProducerDomain>(
            onError = {
                handleError(it)
            },
            onLoadUpdate = {
                dispatch(Result.Loading(it))
            },
            onSuccess = {
                dispatch(Result.Producers(it))
            },
            onEndReached = {
                dispatch(Result.IsListEndReached(true))
            }
        )

        override suspend fun executeAction(
            action: Unit,
            getState: () -> ProducerStore.State
        ) {
            searchManager.listenSearch {
                reset()
                paginator.onLoadNext {
                    getProducers(getState().searchText, offset = it)
                }
            }
        }

        override suspend fun executeIntent(
            intent: ProducerStore.Intent,
            getState: () -> ProducerStore.State
        ) {
            when (intent) {
                ProducerStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                ProducerStore.Intent.OnFilterClicked -> {
                }
                is ProducerStore.Intent.OnProducerClicked -> {
                    publish(ProducerStore.Label.ShowDetail(intent.id))
                }
                ProducerStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is ProducerStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
                is ProducerStore.Intent.OnLoadNext -> paginator.onLoadNext {
                    getProducers(
                        searchText = getState().searchText,
                        offset = it
                    )
                }
            }
        }

        private suspend fun getProducers(searchText: String = "", offset: Long = 0) =
            runCatching {
                val producers = producerInteractor.getProducers(
                    searchQuery = searchText,
                    offset = offset,
                    limit = Paginator.PAGE_SIZE
                )
                producers
            }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.emit("")
            } else {
                publish(ProducerStore.Label.GoBack)
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(ProducerStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        private suspend fun reset() {
            paginator.reset()
            dispatch(Result.IsListEndReached(false))
            dispatch(Result.Producers(listOf()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Producers(val producers: List<ProducerDomain>) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class IsListEndReached(val isListEndReached: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<ProducerStore.State, Result> {
        override fun ProducerStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Producers -> copy(producers = result.producers)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.IsListEndReached -> copy(isListEndReached = result.isListEndReached)
            }
    }
}