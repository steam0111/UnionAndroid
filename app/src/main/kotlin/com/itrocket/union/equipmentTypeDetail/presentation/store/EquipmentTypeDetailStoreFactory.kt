package com.itrocket.union.equipmentTypeDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.equipmentTypeDetail.domain.EquipmentTypeDetailInteractor
import com.itrocket.union.equipmentTypeDetail.domain.entity.EquipmentTypeDetailDomain

class EquipmentTypeDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: EquipmentTypeDetailInteractor,
    private val args: EquipmentTypeDetailArguments
) {

    fun create(): EquipmentTypeDetailStore =
        object : EquipmentTypeDetailStore,
            Store<EquipmentTypeDetailStore.Intent, EquipmentTypeDetailStore.State, EquipmentTypeDetailStore.Label> by storeFactory.create(
                name = "EquipmentTypeDetailStore",
                initialState = EquipmentTypeDetailStore.State(
                    item = EquipmentTypeDetailDomain(emptyList())
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<EquipmentTypeDetailStore.Intent, Unit, EquipmentTypeDetailStore.State, Result, EquipmentTypeDetailStore.Label> =
        EquipmentTypeDetailExecutor()

    private inner class EquipmentTypeDetailExecutor :
        BaseExecutor<EquipmentTypeDetailStore.Intent, Unit, EquipmentTypeDetailStore.State, Result, EquipmentTypeDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> EquipmentTypeDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.EquipmentType(
                        interactor.getEquipmentTypeDetail(args.id)
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(EquipmentTypeDetailStore.Label.Error(throwable.message.orEmpty()))
        }

        override suspend fun executeIntent(
            intent: EquipmentTypeDetailStore.Intent,
            getState: () -> EquipmentTypeDetailStore.State
        ) {
            when (intent) {
                EquipmentTypeDetailStore.Intent.OnBackClicked -> publish(
                    EquipmentTypeDetailStore.Label.GoBack
                )
            }
        }
    }

    private sealed class Result {
        class Loading(val isLoading: Boolean) : Result()
        class EquipmentType(val item: EquipmentTypeDetailDomain) : Result()
    }

    private object ReducerImpl :
        Reducer<EquipmentTypeDetailStore.State, Result> {
        override fun EquipmentTypeDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.EquipmentType -> copy(item = result.item)
            }
    }
}