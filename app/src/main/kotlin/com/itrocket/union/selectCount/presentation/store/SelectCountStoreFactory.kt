package com.itrocket.union.selectCount.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import java.math.BigDecimal

class SelectCountStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val selectCountArguments: SelectCountArguments,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): SelectCountStore =
        object : SelectCountStore,
            Store<SelectCountStore.Intent, SelectCountStore.State, SelectCountStore.Label> by storeFactory.create(
                name = "SelectCountStore",
                initialState = SelectCountStore.State(
                    id = selectCountArguments.id,
                    count = selectCountArguments.count
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<SelectCountStore.Intent, Unit, SelectCountStore.State, Result, SelectCountStore.Label> =
        SelectCountExecutor()

    private inner class SelectCountExecutor :
        BaseExecutor<SelectCountStore.Intent, Unit, SelectCountStore.State, Result, SelectCountStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> SelectCountStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: SelectCountStore.Intent,
            getState: () -> SelectCountStore.State
        ) {
            when (intent) {
                SelectCountStore.Intent.OnAcceptClicked -> publish(
                    SelectCountStore.Label.GoBack(
                        SelectCountResult(
                            id = getState().id,
                            count = getState().count
                        )
                    )
                )
                is SelectCountStore.Intent.OnCountChanged -> dispatch(
                    Result.Count(intent.count.toBigDecimal())
                )
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(SelectCountStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Count(val count: BigDecimal) : Result()
    }

    private object ReducerImpl : Reducer<SelectCountStore.State, Result> {
        override fun SelectCountStore.State.reduce(result: Result) =
            when (result) {
                is Result.Count -> copy(count = result.count)
            }
    }
}