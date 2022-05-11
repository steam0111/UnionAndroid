package com.itrocket.union.auth.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.auth.domain.AuthInteractor
import com.itrocket.union.auth.domain.entity.AuthDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.auth.domain.entity.AuthStep

class AuthStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val authInteractor: AuthInteractor
) {
    fun create(): AuthStore =
        object : AuthStore,
            Store<AuthStore.Intent, AuthStore.State, AuthStore.Label> by storeFactory.create(
                name = "AuthStore",
                initialState = AuthStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<AuthStore.Intent, Unit, AuthStore.State, Result, AuthStore.Label> =
        AuthExecutor()

    private inner class AuthExecutor :
        SuspendExecutor<AuthStore.Intent, Unit, AuthStore.State, Result, AuthStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AuthStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: AuthStore.Intent,
            getState: () -> AuthStore.State
        ) {
            when (intent) {
                AuthStore.Intent.OnPrevClicked -> dispatch(
                    Result.Step(
                        when (getState().currentStep) {
                            AuthStep.AUTH_AND_LICENSE -> AuthStep.AUTH_AND_LICENSE
                            AuthStep.CONNECT_TO_SERVER -> AuthStep.AUTH_AND_LICENSE
                            AuthStep.AUTH_USER -> AuthStep.CONNECT_TO_SERVER
                        }
                    )
                )
                AuthStore.Intent.OnNextClicked ->
                    when (getState().currentStep) {
                        AuthStep.AUTH_AND_LICENSE -> dispatch(Result.Step(AuthStep.CONNECT_TO_SERVER))
                        AuthStep.CONNECT_TO_SERVER -> dispatch(Result.Step(AuthStep.AUTH_USER))
                        AuthStep.AUTH_USER -> {
                            //no-op
                        }
                    }
            }
        }
    }

    private sealed class Result {
        data class Step(val step: AuthStep) : Result()
    }

    private object ReducerImpl : Reducer<AuthStore.State, Result> {
        override fun AuthStore.State.reduce(result: Result) =
            when (result) {
                is Result.Step -> copy(currentStep = result.step)
            }
    }
}