package com.itrocket.union.authUser.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.union.authContainer.presentation.view.AuthContainerComposeFragmentDirections
import com.itrocket.union.authMain.presentation.store.AuthMainArguments

interface AuthUserStore : Store<AuthUserStore.Intent, AuthUserStore.State, AuthUserStore.Label> {

    sealed class Intent {
        data class OnLoginChanged(val login: String) : Intent()
        data class OnPasswordChanged(val password: String) : Intent()
        object OnPasswordVisibilityClicked : Intent()
        object OnNextClicked : Intent()
    }

    data class State(
        val login: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed class Label {
        data class ChangeEnable(val enabled: Boolean) : Label()
        data class ParentLoading(val isLoading: Boolean) : Label()
        data class Error(val message: String) : Label()
        object ShowDocumentMenu : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AuthContainerComposeFragmentDirections.toDocumentsMenu()

        }
    }
}