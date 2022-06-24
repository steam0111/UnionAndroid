package com.itrocket.union.organizationDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.organizationDetail.domain.OrganizationDetailInteractor
import com.itrocket.union.organizationDetail.domain.entity.OrganizationDetailDomain

class OrganizationDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: OrganizationDetailInteractor,
    private val args: OrganizationDetailArguments
) {

    fun create(): OrganizationDetailStore =
        object : OrganizationDetailStore,
            Store<OrganizationDetailStore.Intent, OrganizationDetailStore.State, OrganizationDetailStore.Label> by storeFactory.create(
                name = "OrganizationDetailStore",
                initialState = OrganizationDetailStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<OrganizationDetailStore.Intent, Unit, OrganizationDetailStore.State, Result, OrganizationDetailStore.Label> =
        OrganizationDetailExecutor()

    private inner class OrganizationDetailExecutor :
        BaseExecutor<OrganizationDetailStore.Intent, Unit, OrganizationDetailStore.State, Result, OrganizationDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> OrganizationDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.OrganizationDetail(
                        interactor.getOrganizationDetail(args.id)
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(OrganizationDetailStore.Label.Error(throwable.message.orEmpty()))
        }

        override suspend fun executeIntent(
            intent: OrganizationDetailStore.Intent,
            getState: () -> OrganizationDetailStore.State
        ) {
            when (intent) {
                OrganizationDetailStore.Intent.OnBackClicked -> publish(OrganizationDetailStore.Label.GoBack)
            }
        }
    }

    private sealed class Result {
        class Loading(val isLoading: Boolean) : Result()
        class OrganizationDetail(val item: OrganizationDetailDomain) : Result()
    }

    private object ReducerImpl :
        Reducer<OrganizationDetailStore.State, Result> {
        override fun OrganizationDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.OrganizationDetail -> copy(item = result.item)
            }
    }
}