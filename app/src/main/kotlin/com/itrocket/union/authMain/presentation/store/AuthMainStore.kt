package com.itrocket.union.authMain.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.union.authMain.presentation.view.AuthMainComposeFragmentDirections

interface AuthMainStore : Store<AuthMainStore.Intent, AuthMainStore.State, AuthMainStore.Label> {

    sealed class Intent {
        data class OnPasswordChanged(val password: String) : Intent()
        object OnPasswordVisibilityClicked : Intent()
        object OnSignInClicked : Intent()
        object OnUserChangeClicked : Intent()
        object OnDatabaseSettingsClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val login: String,
        val password: String,
        val enabled: Boolean = false,
        val isPasswordVisible: Boolean = false
    )

    sealed class Label {
        object ShowDocumentMenu : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AuthMainComposeFragmentDirections.toDocumentsMenu()

        }

        data class Error(val message: String) : Label()
    }
}