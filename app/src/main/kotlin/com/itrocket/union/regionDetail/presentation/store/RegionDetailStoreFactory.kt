package com.itrocket.union.regionDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.regionDetail.domain.RegionDetailInteractor
import com.itrocket.union.regionDetail.domain.entity.RegionDetailDomain

class RegionDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: RegionDetailInteractor,
    private val args: RegionDetailArguments
) {

    fun create(): RegionDetailStore =
        object : RegionDetailStore,
            Store<RegionDetailStore.Intent, RegionDetailStore.State, RegionDetailStore.Label> by storeFactory.create(
                name = "RegioneDetailStore",
                initialState = RegionDetailStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<RegionDetailStore.Intent, Unit, RegionDetailStore.State, Result, RegionDetailStore.Label> =
        RegionDetailExecutor()

    private inner class RegionDetailExecutor :
        BaseExecutor<RegionDetailStore.Intent, Unit, RegionDetailStore.State, Result, RegionDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> RegionDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.RegionDetail(
                        interactor.getRegionDetail(args.id)
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(RegionDetailStore.Label.Error(throwable.message.orEmpty()))
        }

        override suspend fun executeIntent(
            intent: RegionDetailStore.Intent,
            getState: () -> RegionDetailStore.State
        ) {
            when (intent) {
                RegionDetailStore.Intent.OnBackClicked -> publish(
                    RegionDetailStore.Label.GoBack
                )
            }
        }
    }

    private sealed class Result {
        class Loading(val isLoading: Boolean) : Result()
        class RegionDetail(val region: RegionDetailDomain) : Result()
    }

    private object ReducerImpl : Reducer<RegionDetailStore.State, Result> {
        override fun RegionDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.RegionDetail -> copy(item = result.region)
            }
    }
}