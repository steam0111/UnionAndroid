package com.itrocket.union.authContainer.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authContainer.domain.AuthContainerInteractor
import com.itrocket.union.authContainer.domain.entity.AuthContainerStep
import com.itrocket.union.authContainer.presentation.view.AuthContainerArguments

class AuthContainerStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val authContainerInteractor: AuthContainerInteractor,
    private val authContainerArguments: AuthContainerArguments
) {
    fun create(): AuthContainerStore =
        object : AuthContainerStore,
            Store<AuthContainerStore.Intent, AuthContainerStore.State, AuthContainerStore.Label> by storeFactory.create(
                name = "AuthStore",
                initialState = AuthContainerStore.State(
                    isShowBackButton = authContainerArguments.isShowBackButton
                ),
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
                AuthContainerStore.Intent.OnBackClicked -> {
                    when {
                        getState().currentStep.ordinal > 0 -> {
                            publish(AuthContainerStore.Label.NavigateBack)
                            dispatch(Result.Enabled(true))
                            dispatch(
                                Result.Step(authContainerInteractor.calculatePrevStep(getState().currentStep))
                            )
                        }
                        getState().currentStep.ordinal == 0 -> {
                            publish(AuthContainerStore.Label.CloseAuthContainer)
                        }
                    }
                }
                AuthContainerStore.Intent.OnNextClicked -> publish(AuthContainerStore.Label.HandleNext)
                AuthContainerStore.Intent.OnNextFinished -> {
                    val nextStep = authContainerInteractor.calculateNextStep(getState().currentStep)
                    if (nextStep != getState().currentStep) {
                        publish(AuthContainerStore.Label.NavigateNext(nextStep))
                        dispatch(Result.Step(nextStep))
                    }
                }
                is AuthContainerStore.Intent.OnEnableChanged -> dispatch(Result.Enabled(intent.enabled))
                is AuthContainerStore.Intent.OnLoadingChanged -> dispatch(Result.IsLoading(intent.isLoading))
            }
        }
    }

    private sealed class Result {
        data class IsLoading(val isLoading: Boolean) : Result()
        data class Enabled(val enabled: Boolean) : Result()
        data class Step(val step: AuthContainerStep) : Result()
    }

    private object ReducerImpl : Reducer<AuthContainerStore.State, Result> {
        override fun AuthContainerStore.State.reduce(result: Result) =
            when (result) {
                is Result.Step -> copy(currentStep = result.step)
                is Result.Enabled -> copy(isEnable = result.enabled)
                is Result.IsLoading -> copy(isLoading = result.isLoading)
            }
    }
}