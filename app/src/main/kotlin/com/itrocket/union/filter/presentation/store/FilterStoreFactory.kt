package com.itrocket.union.filter.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult

class FilterStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val filterInteractor: FilterInteractor,
    private val filterArgs: FilterArguments,
    private val errorInteractor: ErrorInteractor
) {

    fun create(): FilterStore =
        object : FilterStore,
            Store<FilterStore.Intent, FilterStore.State, FilterStore.Label> by storeFactory.create(
                name = "FilterStore",
                initialState = FilterStore.State(
                    params = filterArgs.argument,
                    from = filterArgs.from
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<FilterStore.Intent, Unit, FilterStore.State, Result, FilterStore.Label> =
        FilterExecutor()

    private inner class FilterExecutor :
        BaseExecutor<FilterStore.Intent, Unit, FilterStore.State, Result, FilterStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> FilterStore.State
        ) {
            dispatch(
                Result.Count(
                    filterInteractor.getResultCount(
                        filterArgs.from,
                        filterArgs.argument
                    )
                )
            )
        }

        override suspend fun executeIntent(
            intent: FilterStore.Intent,
            getState: () -> FilterStore.State
        ) {
            when (intent) {
                is FilterStore.Intent.OnFieldClicked -> showFilters(intent.filter, getState)
                is FilterStore.Intent.OnShowUtilizedClick -> {
                    val newFilters = filterInteractor.changeIsShowUtilisedFilter(
                        getState().params,
                        intent.checked
                    )
                    dispatch(Result.Filters(newFilters))
                    dispatch(Result.Count(getResultCount(newFilters, getState())))
                }
                is FilterStore.Intent.OnShowClicked -> {
                    publish(
                        FilterStore.Label.GoBack(
                            result = SelectParamsResult(getState().params)
                        )
                    )
                }
                FilterStore.Intent.OnCrossClicked -> {
                    publish(FilterStore.Label.GoBack())
                }
                FilterStore.Intent.OnDropClicked -> {
                    val droppedFilterFields =
                        filterInteractor.dropFilterFields(getState().params)
                    dispatch(Result.Filters(droppedFilterFields))
                    dispatch(Result.Count(getResultCount(droppedFilterFields, getState())))
                }
                is FilterStore.Intent.OnFilterChanged -> {
                    val filterList = filterInteractor.changeFilters(
                        filters = getState().params,
                        newFilters = intent.filters
                    )
                    dispatch(Result.Filters(filterList))
                    dispatch(Result.Count(getResultCount(filterList, getState())))
                }
            }
        }

        private suspend fun getResultCount(
            params: List<ParamDomain>,
            state: FilterStore.State
        ): Long {
            return filterInteractor.getResultCount(state.from, params)
        }

        private fun showFilters(filter: ParamDomain, getState: () -> FilterStore.State) {
            when (filter.type) {
                ManualType.DATE -> {
                    //no-op
                }
                else -> {
                    publish(
                        FilterStore.Label.ShowFilters(
                            currentFilter = filter,
                            allParams = getState().params
                        )
                    )
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(FilterStore.Label.Error(throwable.message ?: errorInteractor.getDefaultError()))
        }
    }

    private sealed class Result {
        data class Filters(val filters: List<ParamDomain>) : Result()
        data class Count(val count: Long) : Result()
    }

    private object ReducerImpl : Reducer<FilterStore.State, Result> {
        override fun FilterStore.State.reduce(result: Result) =
            when (result) {
                is Result.Filters -> copy(params = result.filters)
                is Result.Count -> copy(resultCount = result.count)
            }
    }
}