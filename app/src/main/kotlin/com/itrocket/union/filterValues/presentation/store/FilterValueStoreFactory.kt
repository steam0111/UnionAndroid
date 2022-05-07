package com.itrocket.union.filterValues.presentation.store

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

class FilterValueStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val filterArgs: FilterValueArguments,
    private val filterInteractor: FilterInteractor
) {
    fun create(): FilterValueStore =
        object : FilterValueStore,
            Store<FilterValueStore.Intent, FilterValueStore.State, FilterValueStore.Label> by storeFactory.create(
                name = "FilterValueStore",
                initialState = FilterValueStore.State(
                    filter = filterArgs.argument,
                    filterValues = filterArgs.argument.values,
                    singleValue = if (filterArgs.argument.values.isNotEmpty()) {
                        filterArgs.argument.values.first()
                    } else {
                        filterArgs.argument.valueList.firstOrNull().orEmpty()
                    }
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<FilterValueStore.Intent, Unit, FilterValueStore.State, Result, FilterValueStore.Label> =
        FilterValueExecutor()

    private inner class FilterValueExecutor :
        SuspendExecutor<FilterValueStore.Intent, Unit, FilterValueStore.State, Result, FilterValueStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> FilterValueStore.State
        ) {
            //no-op
        }

        override suspend fun executeIntent(
            intent: FilterValueStore.Intent,
            getState: () -> FilterValueStore.State
        ) {
            when (intent) {
                FilterValueStore.Intent.OnAcceptClicked -> {
                    val filterValueType = getState().filter.filterValueType
                    val singleValue = getState().singleValue
                    val stateFilterValues = getState().filterValues
                    val filterValues =
                        filterInteractor.getFinalFilterValues(
                            filterValueType = filterValueType,
                            filterValues = stateFilterValues,
                            singleValue = singleValue
                        )
                    publish(
                        FilterValueStore.Label.GoBack(
                            result = getState().filter.copy(values = filterValues)
                        )
                    )
                }
                FilterValueStore.Intent.OnDropClicked -> dispatch(Result.ValuesSelected(filterValues = listOf()))
                is FilterValueStore.Intent.OnValueClicked -> {
                    val newFilterValues =
                        if (getState().filter.filterValueType == FilterValueType.MULTI_SELECT_LIST) {
                            filterInteractor.updateFilterValues(
                                getState().filterValues,
                                intent.filterValue
                            )
                        } else {
                            filterInteractor.updateSingleFilterValues(
                                getState().filterValues,
                                intent.filterValue
                            )
                        }
                    dispatch(Result.ValuesSelected(newFilterValues))
                }
                FilterValueStore.Intent.OnCrossClicked -> publish(
                    FilterValueStore.Label.GoBack(result = getState().filter)
                )
                is FilterValueStore.Intent.OnSingleValueChanged -> {
                    dispatch(Result.SingleValueSelected(intent.value))
                }
                FilterValueStore.Intent.OnCancelClicked -> publish(
                    FilterValueStore.Label.GoBack(
                        null
                    )
                )
            }
        }
    }

    private sealed class Result {
        data class Filter(val filter: FilterDomain) : Result()
        data class ValuesSelected(val filterValues: List<String>) : Result()
        data class SingleValueSelected(val filterValue: String) : Result()
    }

    private object ReducerImpl : Reducer<FilterValueStore.State, Result> {
        override fun FilterValueStore.State.reduce(result: Result) =
            when (result) {
                is Result.ValuesSelected -> copy(filterValues = result.filterValues)
                is Result.Filter -> copy(filter = result.filter)
                is Result.SingleValueSelected -> copy(singleValue = result.filterValue)
            }
    }
}