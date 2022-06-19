package com.itrocket.union.equipmentTypes.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.equipmentTypes.domain.EquipmentTypeInteractor
import com.itrocket.union.equipmentTypes.domain.entity.EquipmentTypesDomain
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class EquipmentTypeStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val typesInteractor: EquipmentTypeInteractor
) {
    fun create(): EquipmentTypeStore =
        object : EquipmentTypeStore,
            Store<EquipmentTypeStore.Intent, EquipmentTypeStore.State, EquipmentTypeStore.Label> by storeFactory.create(
                name = "EquipmentTypesStore",
                initialState = EquipmentTypeStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<EquipmentTypeStore.Intent, Unit, EquipmentTypeStore.State, Result, EquipmentTypeStore.Label> =
        EquipmentTypeExecutor()

    private inner class EquipmentTypeExecutor :
        BaseExecutor<EquipmentTypeStore.Intent, Unit, EquipmentTypeStore.State, Result, EquipmentTypeStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> EquipmentTypeStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                typesInteractor.getEquipmentTypes()
                    .catch { dispatch(Result.Loading(false)) }
                    .collect {
                        dispatch(Result.Types(it))
                        dispatch(Result.Loading(false))
                    }
            }
        }

        override suspend fun executeIntent(
            intent: EquipmentTypeStore.Intent,
            getState: () -> EquipmentTypeStore.State
        ) {
            when (intent) {
                EquipmentTypeStore.Intent.OnBackClicked -> publish(EquipmentTypeStore.Label.GoBack)
                is EquipmentTypeStore.Intent.OnItemClicked -> {}
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(EquipmentTypeStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Types(val types: List<EquipmentTypesDomain>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<EquipmentTypeStore.State, Result> {
        override fun EquipmentTypeStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Types -> copy(types = result.types)
            }
    }
}