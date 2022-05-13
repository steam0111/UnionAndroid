package com.itrocket.union.authMain.presentation.store

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.core.base.CoreDispatchers

class AuthMainStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val authMainInteractor: AuthMainInteractor,
    private val authMainArguments: AuthMainArguments
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
        SuspendExecutor<AuthMainStore.Intent, Unit, AuthMainStore.State, Result, AuthMainStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AuthMainStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: AuthMainStore.Intent,
            getState: () -> AuthMainStore.State
        ) {
            when (intent) {
                is AuthMainStore.Intent.OnPasswordVisibilityClicked -> dispatch(
                    Result.PasswordVisualTransformation(
                        if (intent.passwordVisualTransformation is PasswordVisualTransformation) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        }
                    )
                )
                is AuthMainStore.Intent.OnPasswordChanged -> dispatch(Result.Password(intent.password))
                AuthMainStore.Intent.OnUserChangeClicked -> {

                }
                AuthMainStore.Intent.OnDatabaseSettingsClicked -> {

                }
                AuthMainStore.Intent.OnSignInClicked -> {

                }
            }
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Password(val password: String) : Result()
        data class PasswordVisualTransformation(val visualTransformation: VisualTransformation) :
            Result()
    }

    private object ReducerImpl : Reducer<AuthMainStore.State, Result> {
        override fun AuthMainStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Password -> copy(password = result.password)
                is Result.PasswordVisualTransformation -> copy(passwordVisualTransformation = result.visualTransformation)
            }
    }
}