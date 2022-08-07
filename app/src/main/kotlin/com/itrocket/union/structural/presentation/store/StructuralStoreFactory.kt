package com.itrocket.union.structural.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.union.structural.domain.StructuralInteractor
import com.itrocket.union.structural.domain.entity.StructuralDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.delay

class StructuralStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val structuralInteractor: StructuralInteractor,
    private val structuralArguments: StructuralArguments,
    private val searchManager: SearchManager,
    private val errorInteractor: ErrorInteractor,
) {
    fun create(): StructuralStore =
        object : StructuralStore,
            Store<StructuralStore.Intent, StructuralStore.State, StructuralStore.Label> by storeFactory.create(
                name = "StructuralStore",
                initialState = StructuralStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<StructuralStore.Intent, Unit, StructuralStore.State, Result, StructuralStore.Label> =
        StructuralExecutor()

    private inner class StructuralExecutor :
        BaseExecutor<StructuralStore.Intent, Unit, StructuralStore.State, Result, StructuralStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> StructuralStore.State
        ) {
            dispatch(Result.Loading(true))
            catchException {
                val structuralList =
                    structuralInteractor.getStructuralList(
                        getState().selectStructuralScheme.getOrNull(
                            getState().selectStructuralScheme.lastIndex
                        )
                    )
                dispatch(Result.StructuralValues(structuralList))
            }
            dispatch(Result.Loading(false))
            searchManager.listenSearch {
                search(getState, it)
            }
        }

        override suspend fun executeIntent(
            intent: StructuralStore.Intent,
            getState: () -> StructuralStore.State
        ) {
            when (intent) {
                StructuralStore.Intent.OnBackClicked -> {
                    onBackClicked(getState)
                }
                StructuralStore.Intent.OnAcceptClicked -> goBack(getState)
                StructuralStore.Intent.OnCrossClicked -> publish(StructuralStore.Label.GoBack())
                StructuralStore.Intent.OnFinishClicked -> goBack(getState)
                is StructuralStore.Intent.OnStructuralSelected -> {
                    selectStructural(getState, intent.structural)
                }
                is StructuralStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
            }
        }

        private suspend fun search(getState: () -> StructuralStore.State, searchText: String) {
            val selectedStructuralScheme = getState().selectStructuralScheme.getOrNull(
                getState().selectStructuralScheme.lastIndex
            )
            catchException {
                dispatch(
                    Result.StructuralValues(
                        structuralInteractor.getStructuralList(
                            selectedStructuralScheme,
                            searchText
                        )
                    )
                )
            }
        }

        private fun goBack(getState: () -> StructuralStore.State) {
            publish(
                StructuralStore.Label.GoBack(
                    StructuralResult(
                        StructuralParamDomain(
                            manualType = structuralArguments.structural.manualType,
                            structurals = getState().selectStructuralScheme
                        )
                    )
                )
            )
        }

        private suspend fun selectStructural(
            getState: () -> StructuralStore.State,
            structural: StructuralDomain
        ) {
            val structuralValues = getState().structuralValues
            val selectedStructuralScheme = getState().selectStructuralScheme
            dispatch(
                Result.SelectStructuralScheme(
                    structuralInteractor.resolveNewStructural(
                        selectedStructuralScheme = selectedStructuralScheme,
                        selectedStructural = structural,
                        isRemoveLast = structuralValues.contains(selectedStructuralScheme.lastOrNull())
                    )
                )
            )
            delay(300) //Задержка для того чтобы успела отработать анимация радио баттона выбора местоположения

            dispatch(Result.Loading(true))
            catchException {
                val newStructuralValues =
                    structuralInteractor.getStructuralList(structural)
                if (structuralInteractor.isNewStructuralist(
                        newList = newStructuralValues,
                        oldList = structuralValues
                    )
                ) {
                    dispatch(Result.StructuralValues(newStructuralValues))
                }
            }
            dispatch(Result.SearchText(""))
            dispatch(Result.Loading(false))
        }

        private suspend fun onBackClicked(getState: () -> StructuralStore.State) {
            val selectedStructuralScheme = getState().selectStructuralScheme
            dispatch(Result.Loading(true))
            catchException {
                dispatch(
                    Result.SelectStructuralScheme(
                        structuralInteractor.removeLastStructurals(
                            selectedStructuralScheme = selectedStructuralScheme
                        )
                    )
                )
                val structuralList = structuralInteractor.getStructuralList(
                    getState().selectStructuralScheme.lastOrNull()
                )
                dispatch(Result.StructuralValues(structuralList))
            }
            dispatch(Result.SearchText(""))
            dispatch(Result.Loading(false))
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(StructuralStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class SelectStructuralScheme(val selectStructuralScheme: List<StructuralDomain>) : Result()
        data class StructuralValues(val structuralValues: List<StructuralDomain>) : Result()
        data class SearchText(val searchText: String) : Result()
    }

    private object ReducerImpl : Reducer<StructuralStore.State, Result> {
        override fun StructuralStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.SelectStructuralScheme -> copy(selectStructuralScheme = result.selectStructuralScheme)
                is Result.StructuralValues -> copy(structuralValues = result.structuralValues)
                is Result.SearchText -> copy(searchText = result.searchText)
            }
    }
}