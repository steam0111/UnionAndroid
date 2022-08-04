package com.itrocket.union.authMain.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.utils.ifBlankOrNull

class AuthMainStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val authMainInteractor: AuthMainInteractor,
    private val authMainArguments: AuthMainArguments,
    private val errorInteractor: ErrorInteractor,
) {
    fun create(): AuthMainStore =
        object : AuthMainStore,
            Store<AuthMainStore.Intent, AuthMainStore.State, AuthMainStore.Label> by storeFactory.create(
                name = "AuthMainStore",
                initialState = AuthMainStore.State(
                    login = authMainArguments.login,
                    password = authMainArguments.password
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<AuthMainStore.Intent, Unit, AuthMainStore.State, Result, AuthMainStore.Label> =
        AuthMainExecutor()

    private inner class AuthMainExecutor :
        BaseExecutor<AuthMainStore.Intent, Unit, AuthMainStore.State, Result, AuthMainStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AuthMainStore.State
        ) {
            dispatch(Result.Enabled(authMainInteractor.validatePassword(getState().password)))
        }

        override suspend fun executeIntent(
            intent: AuthMainStore.Intent,
            getState: () -> AuthMainStore.State
        ) {
            when (intent) {
                is AuthMainStore.Intent.OnPasswordVisibilityClicked -> dispatch(
                    Result.PasswordVisible(!getState().isPasswordVisible)
                )
                is AuthMainStore.Intent.OnPasswordChanged -> {
                    dispatch(Result.Password(intent.password))
                    dispatch(Result.Enabled(authMainInteractor.validatePassword(intent.password)))
                }
                AuthMainStore.Intent.OnUserChangeClicked -> {

                }
                AuthMainStore.Intent.OnDatabaseSettingsClicked -> {

                }
                AuthMainStore.Intent.OnSignInClicked -> {
                    dispatch(Result.Loading(true))
                    catchException {
                        authMainInteractor.signIn(
                            login = getState().login,
                            password = getState().password
                        )
                        publish(AuthMainStore.Label.ShowDocumentMenu)
                    }
                    dispatch(Result.Loading(false))
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(
                AuthMainStore.Label.Error(
                    throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }
                )
            )
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Password(val password: String) : Result()
        data class Enabled(val enabled: Boolean) : Result()
        data class PasswordVisible(val isPasswordVisible: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<AuthMainStore.State, Result> {
        override fun AuthMainStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Password -> copy(password = result.password)
                is Result.PasswordVisible -> copy(isPasswordVisible = result.isPasswordVisible)
                is Result.Enabled -> copy(enabled = result.enabled)
            }
    }
}