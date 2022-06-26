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
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.Params
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
                initialState = FilterStore.State(params = Params(filterArgs.argument)),
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
        }

        override suspend fun executeIntent(
            intent: FilterStore.Intent,
            getState: () -> FilterStore.State
        ) {
            when (intent) {
                is FilterStore.Intent.OnFieldClicked -> showFilters(intent.filter, getState)
                is FilterStore.Intent.OnShowClicked -> {
                    publish(
                        FilterStore.Label.GoBack(
                            result = SelectParamsResult(getState().params.paramList)
                        )
                    )
                }
                FilterStore.Intent.OnCrossClicked -> {
                    publish(FilterStore.Label.GoBack())
                }
                FilterStore.Intent.OnDropClicked -> {
                    val droppedFilterFields =
                        filterInteractor.dropFilterFields(getState().params.paramList)
                    dispatch(Result.Filters(droppedFilterFields))
                }
                is FilterStore.Intent.OnFilterChanged -> {
                    val filterList = filterInteractor.changeFilters(
                        filters = getState().params.paramList,
                        newFilters = intent.filters
                    )
                    dispatch(Result.Filters(filterList))
                }
                is FilterStore.Intent.OnFilterLocationChanged -> {
                    dispatch(
                        Result.Filters(
                            filterInteractor.changeLocationFilter(
                                filters = getState().params.paramList,
                                location = intent.locationResult.location
                            )
                        )
                    )
                }
            }
        }

        private fun showFilters(filter: ParamDomain, getState: () -> FilterStore.State) {
            when (filter.type) {
                ManualType.LOCATION -> publish(FilterStore.Label.ShowLocation(filter as LocationParamDomain))
                ManualType.DATE -> {
                    //no-op
                }
                else -> {
                    val defaultTypeParams =
                        filterInteractor.getDefaultTypeParams(getState().params)
                    val currentStep = defaultTypeParams.indexOf(filter) + 1
                    publish(
                        FilterStore.Label.ShowFilters(
                            currentStep = currentStep,
                            filters = defaultTypeParams
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
    }

    private object ReducerImpl : Reducer<FilterStore.State, Result> {
        override fun FilterStore.State.reduce(result: Result) =
            when (result) {
                is Result.Filters -> copy(params = params.copy(result.filters))
            }
    }
}