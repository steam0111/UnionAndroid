package com.itrocket.union.auth.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.union.auth.domain.entity.AuthStep

interface AuthStore : Store<AuthStore.Intent, AuthStore.State, AuthStore.Label> {

    sealed class Intent {
        object OnPrevClicked : Intent()
        object OnNextClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val currentStep: AuthStep = AuthStep.AUTH_AND_LICENSE
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
    }
}