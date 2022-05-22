package com.itrocket.union.authContainer.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.authContainer.domain.entity.AuthContainerStep

interface AuthContainerStore :
    Store<AuthContainerStore.Intent, AuthContainerStore.State, AuthContainerStore.Label> {

    sealed class Intent {
        data class OnEnableChanged(val enabled: Boolean) : Intent()
        object OnBackClicked : Intent()
        object OnNextClicked : Intent()
        object OnNextFinished : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val isEnable: Boolean = false,
        val currentStep: AuthContainerStep = AuthContainerStep.CONNECT_TO_SERVER
    )

    sealed class Label {
        object HandleNext : Label()
        object NavigateBack : Label()
        object CloseAuthContainer : Label(), GoBackNavigationLabel
        data class NavigateNext(val step: AuthContainerStep) : Label()
    }
}