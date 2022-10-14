package com.itrocket.union.nomenclatureGroupDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.nomenclatureGroupDetail.domain.NomenclatureGroupDetailInteractor
import com.itrocket.union.nomenclatureGroupDetail.domain.entity.NomenclatureGroupDetailDomain
import com.itrocket.union.utils.ifBlankOrNull

class NomenclatureGroupDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: NomenclatureGroupDetailInteractor,
    private val args: NomenclatureGroupDetailArguments,
    private val errorInteractor: ErrorInteractor
) {

    fun create(): NomenclatureGroupDetailStore =
        object : NomenclatureGroupDetailStore,
            Store<NomenclatureGroupDetailStore.Intent, NomenclatureGroupDetailStore.State, NomenclatureGroupDetailStore.Label> by storeFactory.create(
                name = "NomenclatureDetailStore",
                initialState = NomenclatureGroupDetailStore.State(
                    item = NomenclatureGroupDetailDomain(emptyList())
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<NomenclatureGroupDetailStore.Intent, Unit, NomenclatureGroupDetailStore.State, Result, NomenclatureGroupDetailStore.Label> =
        NomenclatureDetailExecutor()

    private inner class NomenclatureDetailExecutor :
        BaseExecutor<NomenclatureGroupDetailStore.Intent, Unit, NomenclatureGroupDetailStore.State, Result, NomenclatureGroupDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> NomenclatureGroupDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.Nomenclature(
                        interactor.getNomenclatureGroupDetail(args.id)
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(NomenclatureGroupDetailStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        override suspend fun executeIntent(
            intent: NomenclatureGroupDetailStore.Intent,
            getState: () -> NomenclatureGroupDetailStore.State
        ) {
            when (intent) {
                NomenclatureGroupDetailStore.Intent.OnBackClicked -> publish(
                    NomenclatureGroupDetailStore.Label.GoBack
                )
            }
        }
    }

    private sealed class Result {
        class Loading(val isLoading: Boolean) : Result()
        class Nomenclature(val item: NomenclatureGroupDetailDomain) : Result()
    }

    private object ReducerImpl :
        Reducer<NomenclatureGroupDetailStore.State, Result> {
        override fun NomenclatureGroupDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Nomenclature -> copy(item = result.item)
            }
    }
}