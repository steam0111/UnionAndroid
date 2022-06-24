package com.itrocket.union.nomenclatureDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.nomenclatureDetail.domain.NomenclatureDetailInteractor
import com.itrocket.union.nomenclatureDetail.domain.entity.NomenclatureDetailDomain

class NomenclatureDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: NomenclatureDetailInteractor,
    private val args: NomenclatureDetailArguments
) {

    fun create(): NomenclatureDetailStore =
        object : NomenclatureDetailStore,
            Store<NomenclatureDetailStore.Intent, NomenclatureDetailStore.State, NomenclatureDetailStore.Label> by storeFactory.create(
                name = "NomenclatureDetailStore",
                initialState = NomenclatureDetailStore.State(
                    item = NomenclatureDetailDomain(emptyList())
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<NomenclatureDetailStore.Intent, Unit, NomenclatureDetailStore.State, Result, NomenclatureDetailStore.Label> =
        NomenclatureDetailExecutor()

    private inner class NomenclatureDetailExecutor :
        BaseExecutor<NomenclatureDetailStore.Intent, Unit, NomenclatureDetailStore.State, Result, NomenclatureDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> NomenclatureDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.Nomenclature(
                        interactor.getNomenclatureDetail(args.id)
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(NomenclatureDetailStore.Label.Error(throwable.message.orEmpty()))
        }

        override suspend fun executeIntent(
            intent: NomenclatureDetailStore.Intent,
            getState: () -> NomenclatureDetailStore.State
        ) {
            when (intent) {
                NomenclatureDetailStore.Intent.OnBackClicked -> publish(
                    NomenclatureDetailStore.Label.GoBack
                )
            }
        }
    }

    private sealed class Result {
        class Loading(val isLoading: Boolean) : Result()
        class Nomenclature(val item: NomenclatureDetailDomain) : Result()
    }

    private object ReducerImpl :
        Reducer<NomenclatureDetailStore.State, Result> {
        override fun NomenclatureDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Nomenclature -> copy(item = result.item)
            }
    }
}