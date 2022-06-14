package com.itrocket.union.selectParams.presentation.store

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.ParamValueDomain
import com.itrocket.union.selectParams.domain.SelectParamsInteractor
import com.itrocket.union.selectParams.domain.SelectParamsInteractor.Companion.MIN_CURRENT_STEP
import kotlin.math.max
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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

        private var searchJob: Job = Job()

        override suspend fun executeAction(
            action: Unit,
            getState: () -> SelectParamsStore.State
        ) {
            initialAction(getState)
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
                is SelectParamsStore.Intent.OnSearchTextChanged -> {
                    onSearchTextChanged(getState, intent.searchText)
                }
                SelectParamsStore.Intent.OnCrossClicked -> {
                    publish(SelectParamsStore.Label.GoBack())
                }
                is SelectParamsStore.Intent.OnItemSelected -> {
                    onItemSelected(getState, intent.item)
                }
                SelectParamsStore.Intent.OnNextClicked -> {
                    onNextClicked(getState)
                }
                SelectParamsStore.Intent.OnPrevClicked -> {
                    onPrevClicked(getState)
                }
            }
        }

        private suspend fun initialAction(getState: () -> SelectParamsStore.State) {
            val currentParam = getState().params[getState().currentStep - 1]
            dispatch(Result.Loading(true))
            dispatch(
                Result.SearchText(
                    TextFieldValue(
                        text = currentParam.paramValue?.value.orEmpty(),
                        selection = TextRange(currentParam.paramValue?.value?.length ?: 0)
                    )
                )
            )
            dispatch(
                Result.Values(
                    selectParamsInteractor.getParamValues(
                        currentParam.type,
                        currentParam.paramValue?.value.orEmpty()
                    )
                )
            )
            dispatch(Result.Loading(false))
        }

        private suspend fun onSearchTextChanged(
            getState: () -> SelectParamsStore.State,
            searchText: TextFieldValue
        ) {
            if (searchText.text != getState().searchText.text) {
                dispatch(Result.SearchText(searchText))

                dispatch(Result.Loading(true))
                searchJob.cancel()
                searchJob = Job()
                withContext(searchJob + coreDispatchers.io) {
                    delay(SEARCH_DELAY)
                    withContext(coreDispatchers.ui) {
                        val currentParam = getState().params[getState().currentStep - 1]

                        dispatchValues(currentParam.type, searchText.text)
                        dispatch(Result.Loading(false))
                    }
                }
            }
        }

        private suspend fun onItemSelected(
            getState: () -> SelectParamsStore.State,
            item: ParamValueDomain
        ) {
            dispatch(Result.Loading(true))

            val newParams = selectParamsInteractor.changeParamValue(
                params = getState().params,
                currentStep = getState().currentStep,
                paramValue = item
            )
            dispatch(Result.Params(newParams))

            val currentParam = getState().params[getState().currentStep - 1]

            dispatchSearchText(currentParam.paramValue?.value.orEmpty())
            dispatchValues(
                currentParam.type,
                currentParam.paramValue?.value.orEmpty()
            )
            dispatch(Result.Loading(false))
        }

        private suspend fun onPrevClicked(getState: () -> SelectParamsStore.State) {
            dispatch(Result.Loading(true))
            dispatch(Result.Step(max(getState().currentStep - 1, MIN_CURRENT_STEP)))

            val currentParam = getState().params[getState().currentStep - 1]

            dispatchSearchText(currentParam.paramValue?.value.orEmpty())
            dispatchValues(
                currentParam.type,
                currentParam.paramValue?.value.orEmpty()
            )
            dispatch(Result.Loading(false))
        }

        private suspend fun onNextClicked(getState: () -> SelectParamsStore.State) {
            if (getState().currentStep == getState().params.size) {
                publish(
                    SelectParamsStore.Label.GoBack(
                        result = SelectParamsResult(getState().params)
                    )
                )
            } else {
                dispatch(Result.Loading(true))
                dispatch(Result.Step(getState().currentStep + 1))

                val currentParam = getState().params[getState().currentStep - 1]

                dispatchSearchText(currentParam.paramValue?.value.orEmpty())
                dispatchValues(
                    currentParam.type,
                    currentParam.paramValue?.value.orEmpty()
                )
                dispatch(Result.Loading(false))
            }
        }

        private fun dispatchSearchText(searchText: String) {
            dispatch(
                Result.SearchText(
                    TextFieldValue(
                        text = searchText,
                        selection = TextRange(searchText.length)
                    )
                )
            )
        }

        private suspend fun dispatchValues(type: ManualType, searchText: String) {
            dispatch(
                Result.Values(
                    selectParamsInteractor.getParamValues(
                        type,
                        searchText
                    )
                )
            )
        }

    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Step(val currentStep: Int) : Result()
        data class Params(val params: List<ParamDomain>) : Result()
        data class Values(val values: List<ParamValueDomain>) : Result()
        data class SearchText(val searchText: TextFieldValue) : Result()
    }

    private object ReducerImpl : Reducer<SelectParamsStore.State, Result> {
        override fun SelectParamsStore.State.reduce(result: Result) =
            when (result) {
                is Result.Step -> copy(currentStep = result.currentStep)
                is Result.Params -> copy(params = result.params)
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.Values -> copy(currentParamValues = result.values)
            }
    }

    companion object {
        private const val SEARCH_DELAY = 200L
    }
}