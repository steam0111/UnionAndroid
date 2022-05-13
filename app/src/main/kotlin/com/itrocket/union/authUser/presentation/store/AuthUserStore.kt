package com.itrocket.union.authUser.presentation.store

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.arkivanov.mvikotlin.core.store.Store

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
        object NextFinish : Label()
    }
}