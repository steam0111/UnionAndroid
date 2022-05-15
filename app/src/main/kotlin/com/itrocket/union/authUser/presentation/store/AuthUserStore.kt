package com.itrocket.union.authUser.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store

interface AuthUserStore : Store<AuthUserStore.Intent, AuthUserStore.State, AuthUserStore.Label> {

    sealed class Intent {
        data class OnLoginChanged(val login: String) : Intent()
        data class OnPasswordChanged(val password: String) : Intent()
    }

    data class State(
        val login: String = "",
        val password: String = "",
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
    }
}