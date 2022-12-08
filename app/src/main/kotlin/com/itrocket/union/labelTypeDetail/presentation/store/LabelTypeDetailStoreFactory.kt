package com.itrocket.union.labelTypeDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.union.labelTypeDetail.domain.LabelTypeDetailInteractor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.labelTypeDetail.domain.entity.LabelTypeDetailDomain

class LabelTypeDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val labelTypeDetailInteractor: LabelTypeDetailInteractor,
    private val labelTypeDetailArguments: LabelTypeDetailArguments,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): LabelTypeDetailStore =
        object : LabelTypeDetailStore,
            Store<LabelTypeDetailStore.Intent, LabelTypeDetailStore.State, LabelTypeDetailStore.Label> by storeFactory.create(
                name = "LabelTypeDetailStore",
                initialState = LabelTypeDetailStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<LabelTypeDetailStore.Intent, Unit, LabelTypeDetailStore.State, Result, LabelTypeDetailStore.Label> =
        LabelTypeDetailExecutor()

    private inner class LabelTypeDetailExecutor :
        BaseExecutor<LabelTypeDetailStore.Intent, Unit, LabelTypeDetailStore.State, Result, LabelTypeDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> LabelTypeDetailStore.State
        ) {
            dispatch(Result.Loading(true))
            catchException {
                dispatch(
                    Result.LabelType(
                        labelTypeDetailInteractor.getLabelTypeById(labelTypeDetailArguments.id)
                    )
                )
            }
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: LabelTypeDetailStore.Intent,
            getState: () -> LabelTypeDetailStore.State
        ) {
            when (intent) {
                LabelTypeDetailStore.Intent.OnBackClicked -> publish(LabelTypeDetailStore.Label.GoBack)
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(LabelTypeDetailStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class LabelType(val item: LabelTypeDetailDomain) : Result()
    }

    private object ReducerImpl : Reducer<LabelTypeDetailStore.State, Result> {
        override fun LabelTypeDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.LabelType -> copy(item = result.item)
            }
    }
}