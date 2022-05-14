package com.itrocket.union.authSelectUser.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.authSelectUser.domain.AuthSelectUserInteractor
import com.itrocket.union.authSelectUser.domain.entity.AuthSelectUserDomain
import com.itrocket.core.base.CoreDispatchers

class AuthSelectUserStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val authSelectUserInteractor: AuthSelectUserInteractor
) {
    fun create(): AuthSelectUserStore =
        object : AuthSelectUserStore,
            Store<AuthSelectUserStore.Intent, AuthSelectUserStore.State, AuthSelectUserStore.Label> by storeFactory.create(
                name = "AuthSelectUserStore",
                initialState = AuthSelectUserStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<AuthSelectUserStore.Intent, Unit, AuthSelectUserStore.State, Result, AuthSelectUserStore.Label> =
        AuthSelectUserExecutor()

    private inner class AuthSelectUserExecutor :
        SuspendExecutor<AuthSelectUserStore.Intent, Unit, AuthSelectUserStore.State, Result, AuthSelectUserStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> AuthSelectUserStore.State
        ) {
            dispatch(Result.Loading(true))
            dispatch(Result.UserList(authSelectUserInteractor.getExistUsers("")))
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: AuthSelectUserStore.Intent,
            getState: () -> AuthSelectUserStore.State
        ) {
            when (intent) {
                AuthSelectUserStore.Intent.OnCrossClicked -> publish(AuthSelectUserStore.Label.GoBack())
                is AuthSelectUserStore.Intent.OnUserSearchTextChanged -> {
                    dispatch(Result.Loading(true))
                    dispatch(
                        Result.SearchText(intent.search)
                    )
                    dispatch(Result.UserList(authSelectUserInteractor.getExistUsers(intent.search)))
                    dispatch(Result.Loading(false))
                }
                is AuthSelectUserStore.Intent.OnUserSelected -> dispatch(Result.UserSelected(intent.user))
                AuthSelectUserStore.Intent.OnAcceptClicked -> publish(
                    AuthSelectUserStore.Label.GoBack(
                        if (getState().selectedUser.isNotBlank()) {
                            AuthSelectUserResult(getState().selectedUser)
                        } else {
                            null
                        }
                    )
                )
                AuthSelectUserStore.Intent.OnCancelClicked -> publish(AuthSelectUserStore.Label.GoBack())
            }
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class UserSelected(val user: String) : Result()
        data class SearchText(val searchText: String) : Result()
        data class UserList(val users: List<String>) : Result()
    }

    private object ReducerImpl : Reducer<AuthSelectUserStore.State, Result> {
        override fun AuthSelectUserStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.UserSelected -> copy(selectedUser = result.user)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.UserList -> copy(userList = result.users)
            }
    }
}