package com.itrocket.union.filter.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filter.domain.entity.FilterValueType

class FilterStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val filterInteractor: FilterInteractor,
    private val filterArgs: FilterArguments
) {
    fun create(): FilterStore =
        object : FilterStore,
            Store<FilterStore.Intent, FilterStore.State, FilterStore.Label> by storeFactory.create(
                name = "FilterStore",
                initialState = FilterStore.State(filterFields = filterArgs.argument),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<FilterStore.Intent, Unit, FilterStore.State, Result, FilterStore.Label> =
        FilterExecutor()

    private inner class FilterExecutor :
        SuspendExecutor<FilterStore.Intent, Unit, FilterStore.State, Result, FilterStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> FilterStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: FilterStore.Intent,
            getState: () -> FilterStore.State
        ) {
            when (intent) {
                is FilterStore.Intent.OnFieldClicked -> {
                    if (intent.filter.filterValueType == FilterValueType.LOCATION) {
                        //publish(FilterStore.Label.ShowLocation(intent.filter))
                    } else {
                        publish(FilterStore.Label.ShowFilterValues(intent.filter))
                    }
                }
                is FilterStore.Intent.OnShowClicked -> {

                }
                FilterStore.Intent.OnCrossClicked -> {
                    publish(FilterStore.Label.GoBack)
                }
                FilterStore.Intent.OnDropClicked -> {
                    val droppedFilterFields =
                        filterInteractor.dropFilterFields(getState().filterFields)
                    dispatch(Result.Filters(droppedFilterFields))
                }
                is FilterStore.Intent.OnFilterChanged -> {
                    val filterList = filterInteractor.changeFilter(
                        filters = getState().filterFields,
                        filterChange = intent.filter
                    )
                    dispatch(Result.Filters(filterList))
                }
                is FilterStore.Intent.OnFilterLocationChanged -> {
                    dispatch(
                        Result.Filters(
                            filterInteractor.changeLocationFilter(
                                filters = getState().filterFields,
                                location = intent.locationResult.location
                            )
                        )
                    )
                }
            }
        }
    }

    private sealed class Result {
        data class Filters(val filters: List<FilterDomain>) : Result()
    }

    private object ReducerImpl : Reducer<FilterStore.State, Result> {
        override fun FilterStore.State.reduce(result: Result) =
            when (result) {
                is Result.Filters -> copy(filterFields = result.filters)
            }
    }
}