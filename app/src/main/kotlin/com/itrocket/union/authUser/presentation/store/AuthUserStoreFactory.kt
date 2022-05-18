package com.itrocket.union.authUser.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authUser.domain.AuthUserInteractor

class AuthUserStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val authUserInteractor: AuthUserInteractor,
    private val initialState: AuthUserStore.State?
) {
    fun create(): AuthUserStore =
        object : AuthUserStore,
            Store<AuthUserStore.Intent, AuthUserStore.State, AuthUserStore.Label> by storeFactory.create(
                name = "AuthUserStore",
                initialState = initialState ?: AuthUserStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<AuthUserStore.Intent, Unit, AuthUserStore.State, Result, AuthUserStore.Label> =
        AuthUserExecutor()

    private inner class AuthUserExecutor :
        SuspendExecutor<AuthUserStore.Intent, Unit, AuthUserStore.State, Result, AuthUserStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AuthUserStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: AuthUserStore.Intent,
            getState: () -> AuthUserStore.State
        ) {
            when (intent) {
                is AuthUserStore.Intent.OnLoginChanged -> dispatch(
                    Result.Login(intent.login)
                )
                is AuthUserStore.Intent.OnPasswordChanged -> dispatch(Result.Password(intent.password))
                AuthUserStore.Intent.OnNextClicked -> publish(AuthUserStore.Label.NextFinish)
                is AuthUserStore.Intent.OnPasswordVisibilityClicked -> dispatch(
                    Result.PasswordVisible(!getState().isPasswordVisible)
                )
            }
        }
    }

    private sealed class Result {
        data class Login(val login: String) : Result()
        data class Password(val password: String) : Result()
        data class PasswordVisible(val isPasswordVisible: Boolean) :
            Result()
    }

    private object ReducerImpl : Reducer<AuthUserStore.State, Result> {
        override fun AuthUserStore.State.reduce(result: Result) =
            when (result) {
                is Result.Login -> copy(login = result.login)
                is Result.Password -> copy(password = result.password)
                is Result.PasswordVisible -> copy(isPasswordVisible = result.isPasswordVisible)
            }
    }
}