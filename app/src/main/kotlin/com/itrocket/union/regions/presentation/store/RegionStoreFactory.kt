package com.itrocket.union.regions.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.union.regions.domain.RegionInteractor
import com.itrocket.union.regions.domain.entity.RegionDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class RegionStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val regionInteractor: RegionInteractor,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): RegionStore =
        object : RegionStore,
            Store<RegionStore.Intent, RegionStore.State, RegionStore.Label> by storeFactory.create(
                name = "RegionStore",
                initialState = RegionStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<RegionStore.Intent, Unit, RegionStore.State, Result, RegionStore.Label> =
        RegionExecutor()

    private inner class RegionExecutor :
        BaseExecutor<RegionStore.Intent, Unit, RegionStore.State, Result, RegionStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> RegionStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                regionInteractor.getRegions()
                    .catch { handleError(it) }
                    .collect {
                        dispatch(Result.Regions(it))
                        dispatch(Result.Loading(false))
                    }
            }
        }

        override suspend fun executeIntent(
            intent: RegionStore.Intent,
            getState: () -> RegionStore.State
        ) {
            when (intent) {
                RegionStore.Intent.OnBackClicked -> publish(RegionStore.Label.GoBack)
                RegionStore.Intent.OnFilterClicked -> {
                }
                is RegionStore.Intent.OnRegionClicked -> {
                    publish(RegionStore.Label.ShowDetail(intent.id))
                }
                RegionStore.Intent.OnSearchClicked -> {
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(RegionStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private sealed class Result {
        data class Regions(val regions: List<RegionDomain>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<RegionStore.State, Result> {
        override fun RegionStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Regions -> copy(regions = result.regions)
            }
    }
}