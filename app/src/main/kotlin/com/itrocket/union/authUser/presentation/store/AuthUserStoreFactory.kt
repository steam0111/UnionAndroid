package com.itrocket.union.authUser.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.authUser.domain.AuthUserInteractor
import com.itrocket.union.container.domain.IsDbSyncedUseCase
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.utils.ifBlankOrNull
import kotlin.math.log

class AuthUserStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val authUserInteractor: AuthUserInteractor,
    private val authMainInteractor: AuthMainInteractor,
    private val initialState: AuthUserStore.State?,
    private val errorInteractor: ErrorInteractor,
    private val isDbSyncedUseCase: IsDbSyncedUseCase
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
        BaseExecutor<AuthUserStore.Intent, Unit, AuthUserStore.State, Result, AuthUserStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AuthUserStore.State
        ) {
            val login = authMainInteractor.getLogin()
            if (!login.isNullOrBlank()) {
                dispatch(Result.Login(login))
            }
            publish(
                AuthUserStore.Label.ChangeEnable(
                    isEnabled(
                        getState().login,
                        getState().password
                    )
                )
            )
        }

        override suspend fun executeIntent(
            intent: AuthUserStore.Intent,
            getState: () -> AuthUserStore.State
        ) {
            when (intent) {
                is AuthUserStore.Intent.OnLoginChanged -> {
                    dispatch(
                        Result.Login(intent.login)
                    )
                    publish(
                        AuthUserStore.Label.ChangeEnable(
                            isEnabled(
                                getState().login,
                                getState().password
                            )
                        )
                    )
                }
                is AuthUserStore.Intent.OnPasswordChanged -> {
                    dispatch(Result.Password(intent.password))
                    publish(
                        AuthUserStore.Label.ChangeEnable(
                            isEnabled(
                                getState().login,
                                getState().password
                            )
                        )
                    )
                }
                AuthUserStore.Intent.OnNextClicked -> {
                    publish(AuthUserStore.Label.ParentLoading(true))
                    catchException {
                        authMainInteractor.signIn(
                            login = getState().login,
                            password = getState().password
                        )
                        if (isDbSyncedUseCase.execute()) {
                            publish(AuthUserStore.Label.ShowDocumentMenu)
                        } else {
                            publish(AuthUserStore.Label.ShowDbSync)
                        }
                    }
                    publish(AuthUserStore.Label.ParentLoading(false))
                }
                is AuthUserStore.Intent.OnPasswordVisibilityClicked -> dispatch(
                    Result.PasswordVisible(!getState().isPasswordVisible)
                )
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(AuthUserStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private fun isEnabled(login: String, password: String) =
        authUserInteractor.validateLogin(login) && authUserInteractor.validatePassword(password)

    private sealed class Result {
        data class Login(val login: String) : Result()
        data class Password(val password: String) : Result()
        data class PasswordVisible(val isPasswordVisible: Boolean) : Result()
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