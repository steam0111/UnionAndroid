package com.itrocket.union.organizations.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authUser.presentation.store.AuthUserStore
import com.itrocket.union.organizations.domain.OrganizationInteractor
import com.itrocket.union.organizations.domain.entity.OrganizationDomain

class OrganizationStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val organizationInteractor: OrganizationInteractor,
) {
    fun create(): OrganizationStore =
        object : OrganizationStore,
            Store<OrganizationStore.Intent, OrganizationStore.State, OrganizationStore.Label> by storeFactory.create(
                name = "OrganizationsStore",
                initialState = OrganizationStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<OrganizationStore.Intent, Unit, OrganizationStore.State, Result, OrganizationStore.Label> =
        OrganizationExecutor()

    private inner class OrganizationExecutor :
        BaseExecutor<OrganizationStore.Intent, Unit, OrganizationStore.State, Result, OrganizationStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> OrganizationStore.State
        ) {
            dispatch(Result.Loading(true))
            catchException {
                dispatch(Result.Organizations(organizationInteractor.getOrganizations()))
            }
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: OrganizationStore.Intent,
            getState: () -> OrganizationStore.State
        ) {
            when (intent) {
                OrganizationStore.Intent.OnBackClicked -> publish(OrganizationStore.Label.GoBack)
                OrganizationStore.Intent.OnFilterClicked -> {}
                OrganizationStore.Intent.OnSearchClicked -> {}
                is OrganizationStore.Intent.OnOrganizationsClicked -> {}
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(OrganizationStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Organizations(val organizations: List<OrganizationDomain>) : Result()
    }

    private object ReducerImpl : Reducer<OrganizationStore.State, Result> {
        override fun OrganizationStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Organizations -> copy(organizations = result.organizations)
            }
    }
}