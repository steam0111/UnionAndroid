package com.itrocket.union.authContainer.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.authContainer.domain.AuthContainerInteractor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authContainer.domain.entity.AuthContainerStep

class AuthContainerStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val authContainerInteractor: AuthContainerInteractor
) {
    fun create(): AuthContainerStore =
        object : AuthContainerStore,
            Store<AuthContainerStore.Intent, AuthContainerStore.State, AuthContainerStore.Label> by storeFactory.create(
                name = "AuthStore",
                initialState = AuthContainerStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<AuthContainerStore.Intent, Unit, AuthContainerStore.State, Result, AuthContainerStore.Label> =
        AuthExecutor()

    private inner class AuthExecutor :
        SuspendExecutor<AuthContainerStore.Intent, Unit, AuthContainerStore.State, Result, AuthContainerStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AuthContainerStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: AuthContainerStore.Intent,
            getState: () -> AuthContainerStore.State
        ) {
            when (intent) {
                AuthContainerStore.Intent.OnBackClicked -> dispatch(
                    Result.Step(authContainerInteractor.calculatePrevStep(getState().currentStep))
                )
                AuthContainerStore.Intent.OnNextClicked -> dispatch(
                    Result.Step(authContainerInteractor.calculateNextStep(getState().currentStep))
                )
            }
        }
    }

    private sealed class Result {
        data class Step(val step: AuthContainerStep) : Result()
    }

    private object ReducerImpl : Reducer<AuthContainerStore.State, Result> {
        override fun AuthContainerStore.State.reduce(result: Result) =
            when (result) {
                is Result.Step -> copy(currentStep = result.step)
            }
    }
}