package com.itrocket.union.nomenclature.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.nomenclature.domain.NomenclatureInteractor
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain
import com.itrocket.union.utils.ifBlankOrNull

class NomenclatureStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val nomenclatureInteractor: NomenclatureInteractor,
    private val nomenclatureArguments: NomenclatureArguments,
    private val errorInteractor: ErrorInteractor
) {
    private var params: List<ParamDomain>? = null

    fun create(): NomenclatureStore =
        object : NomenclatureStore,
            Store<NomenclatureStore.Intent, NomenclatureStore.State, NomenclatureStore.Label> by storeFactory.create(
                name = "NomenclatureStore",
                initialState = NomenclatureStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<NomenclatureStore.Intent, Unit, NomenclatureStore.State, Result, NomenclatureStore.Label> =
        NomenclatureExecutor()

    private inner class NomenclatureExecutor :
        BaseExecutor<NomenclatureStore.Intent, Unit, NomenclatureStore.State, Result, NomenclatureStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> NomenclatureStore.State
        ) {
            getNomenclatures()
        }

        override suspend fun executeIntent(
            intent: NomenclatureStore.Intent,
            getState: () -> NomenclatureStore.State
        ) {
            when (intent) {
                NomenclatureStore.Intent.OnBackClicked -> publish(NomenclatureStore.Label.GoBack)
                is NomenclatureStore.Intent.OnItemClicked -> publish(
                    NomenclatureStore.Label.ShowDetail(
                        intent.id
                    )
                )
                is NomenclatureStore.Intent.OnFilterClicked -> publish(
                    NomenclatureStore.Label.ShowFilter(
                        params ?: nomenclatureInteractor.getFilters()
                    )
                )
                is NomenclatureStore.Intent.OnFilterResult -> {
                    params = intent.params
                    getNomenclatures(params)
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(NomenclatureStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }

        private suspend fun getNomenclatures(params: List<ParamDomain>? = null) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(Result.Nomenclatures(nomenclatureInteractor.getNomenclatures(params)))
                dispatch(Result.Loading(false))
            }
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Nomenclatures(val nomenclatures: List<NomenclatureDomain>) : Result()
    }

    private object ReducerImpl : Reducer<NomenclatureStore.State, Result> {
        override fun NomenclatureStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Nomenclatures -> copy(nomenclatures = result.nomenclatures)
            }
    }
}