package com.itrocket.union.nomenclatureGroup.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.nomenclatureGroup.domain.NomenclatureGroupInteractor
import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain

class NomenclatureGroupStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val nomenclatureGroupInteractor: NomenclatureGroupInteractor,
    private val nomenclatureGroupArguments: NomenclatureGroupArguments
) {
    fun create(): NomenclatureGroupStore =
        object : NomenclatureGroupStore,
            Store<NomenclatureGroupStore.Intent, NomenclatureGroupStore.State, NomenclatureGroupStore.Label> by storeFactory.create(
                name = "NomenclatureGroupStore",
                initialState = NomenclatureGroupStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<NomenclatureGroupStore.Intent, Unit, NomenclatureGroupStore.State, Result, NomenclatureGroupStore.Label> =
        NomenclatureGroupExecutor()

    private inner class NomenclatureGroupExecutor :
        BaseExecutor<NomenclatureGroupStore.Intent, Unit, NomenclatureGroupStore.State, Result, NomenclatureGroupStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> NomenclatureGroupStore.State
        ) {
            catchException {
                dispatch(Result.NomenclatureGroups(nomenclatureGroupInteractor.getNomenclatureGroups()))
            }
        }

        override suspend fun executeIntent(
            intent: NomenclatureGroupStore.Intent,
            getState: () -> NomenclatureGroupStore.State
        ) {
            when (intent) {
                NomenclatureGroupStore.Intent.OnBackClicked -> publish(NomenclatureGroupStore.Label.GoBack)
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(NomenclatureGroupStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class NomenclatureGroups(val nomenclatureGroupsDomain: List<NomenclatureGroupDomain>) : Result()
    }

    private object ReducerImpl : Reducer<NomenclatureGroupStore.State, Result> {
        override fun NomenclatureGroupStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.NomenclatureGroups -> copy(nomenclatureGroups = result.nomenclatureGroupsDomain)
            }
    }
}