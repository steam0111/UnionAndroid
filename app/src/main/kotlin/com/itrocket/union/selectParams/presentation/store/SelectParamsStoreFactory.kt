package com.itrocket.union.selectParams.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.selectParams.domain.ParamDomain
import com.itrocket.union.selectParams.domain.SelectParamsInteractor
import com.itrocket.union.selectParams.domain.SelectParamsInteractor.Companion.MIN_CURRENT_STEP
import kotlin.math.max

class SelectParamsStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val selectParamsInteractor: SelectParamsInteractor,
    private val selectParamsArguments: SelectParamsArguments
) {
    fun create(): SelectParamsStore =
        object : SelectParamsStore,
            Store<SelectParamsStore.Intent, SelectParamsStore.State, SelectParamsStore.Label> by storeFactory.create(
                name = "SelectParamsStore",
                initialState = SelectParamsStore.State(
                    currentStep = selectParamsArguments.currentStep,
                    params = selectParamsArguments.params
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<SelectParamsStore.Intent, Unit, SelectParamsStore.State, Result, SelectParamsStore.Label> =
        SelectParamsExecutor()

    private inner class SelectParamsExecutor :
        SuspendExecutor<SelectParamsStore.Intent, Unit, SelectParamsStore.State, Result, SelectParamsStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> SelectParamsStore.State
        ) {
            dispatch(Result.Loading(true))
            dispatch(
                Result.Values(
                    selectParamsInteractor.getParamValues(getState().params[getState().currentStep - 1].title)
                )
            )
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: SelectParamsStore.Intent,
            getState: () -> SelectParamsStore.State
        ) {
            when (intent) {
                SelectParamsStore.Intent.OnAcceptClicked -> {
                    publish(
                        SelectParamsStore.Label.GoBack(
                            result = SelectParamsResult(getState().params)
                        )
                    )
                }
                SelectParamsStore.Intent.OnCrossClicked -> {
                    publish(SelectParamsStore.Label.GoBack())
                }
                is SelectParamsStore.Intent.OnItemSelected -> {
                    dispatch(
                        Result.Params(
                            selectParamsInteractor.changeParamValue(
                                params = getState().params,
                                currentStep = getState().currentStep,
                                paramValue = intent.item
                            )
                        )
                    )
                }
                SelectParamsStore.Intent.OnNextClicked -> {
                    if (getState().currentStep == getState().params.size) {
                        publish(
                            SelectParamsStore.Label.GoBack(
                                result = SelectParamsResult(getState().params)
                            )
                        )
                    } else {
                        dispatch(Result.Loading(true))
                        dispatch(Result.Step(getState().currentStep + 1))
                        dispatch(
                            Result.Values(
                                selectParamsInteractor.getParamValues(getState().params[getState().currentStep - 1].title)
                            )
                        )
                        dispatch(Result.Loading(false))
                    }
                }
                SelectParamsStore.Intent.OnPrevClicked -> {
                    dispatch(Result.Loading(true))
                    dispatch(Result.Step(max(getState().currentStep - 1, MIN_CURRENT_STEP)))
                    dispatch(
                        Result.Values(
                            selectParamsInteractor.getParamValues(getState().params[getState().currentStep - 1].title)
                        )
                    )
                    dispatch(Result.Loading(false))
                }
            }
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Step(val currentStep: Int) : Result()
        data class Params(val params: List<ParamDomain>) : Result()
        data class Values(val values: List<String>) : Result()
    }

    private object ReducerImpl : Reducer<SelectParamsStore.State, Result> {
        override fun SelectParamsStore.State.reduce(result: Result) =
            when (result) {
                is Result.Step -> copy(currentStep = result.currentStep)
                is Result.Params -> copy(params = result.params)
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Values -> copy(currentParamValues = result.values)
            }
    }
}