package com.itrocket.union.authContainer.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.union.authContainer.domain.entity.AuthContainerStep

interface AuthContainerStore : Store<AuthContainerStore.Intent, AuthContainerStore.State, AuthContainerStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        object OnNextClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val currentStep: AuthContainerStep = AuthContainerStep.AUTH_AND_LICENSE
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
    }
}