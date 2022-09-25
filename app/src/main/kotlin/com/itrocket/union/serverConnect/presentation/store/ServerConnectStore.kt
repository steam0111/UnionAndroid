package com.itrocket.union.serverConnect.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.theme.domain.entity.Medias

interface ServerConnectStore :
    Store<ServerConnectStore.Intent, ServerConnectStore.State, ServerConnectStore.Label> {

    sealed class Intent {
        data class OnServerAddressChanged(val serverAddress: String) : Intent()
        data class OnPortChanged(val port: String) : Intent()
        object OnNextClicked : Intent()
    }

    data class State(
        val serverAddress: String = "",
        val port: String = "",
        val medias: Medias? = null
    )

    sealed class Label {
        data class ChangeEnable(val enabled: Boolean) : Label()
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ParentLoading(val isLoading: Boolean) : Label()
        object NextFinish : Label()
    }
}