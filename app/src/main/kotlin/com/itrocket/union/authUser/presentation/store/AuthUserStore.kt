package com.itrocket.union.authUser.presentation.store

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.union.authContainer.presentation.view.AuthContainerComposeFragmentDirections
import com.itrocket.union.authMain.presentation.store.AuthMainArguments

interface AuthUserStore : Store<AuthUserStore.Intent, AuthUserStore.State, AuthUserStore.Label> {

    sealed class Intent {
        data class OnLoginChanged(val login: String) : Intent()
        data class OnPasswordChanged(val password: String) : Intent()
        data class OnPasswordVisibilityClicked(val passwordVisualTransformation: VisualTransformation) :
            Intent()

        object OnNextClicked : Intent()
    }

    data class State(
        val login: String = "",
        val password: String = "",
        val passwordVisualTransformation: VisualTransformation = PasswordVisualTransformation()
    )

    sealed class Label {
        data class ShowAuthMain(val login: String, val password: String) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AuthContainerComposeFragmentDirections.toAuthMain(
                    AuthMainArguments(
                        login = login,
                        password = password
                    )
                )

        }
    }
}