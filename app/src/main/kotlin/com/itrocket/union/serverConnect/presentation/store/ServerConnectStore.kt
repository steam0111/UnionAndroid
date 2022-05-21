package com.itrocket.union.serverConnect.presentation.store

import com.arkivanov.mvikotlin.core.store.Store

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
    )

    sealed class Label {
        data class ChangeEnable(val enabled: Boolean) : Label()
        object NextFinish : Label()
    }
}