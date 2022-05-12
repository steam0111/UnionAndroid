package com.itrocket.union.serverConnect.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.serverConnect.ServerConnectModule.SERVERCONNECT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.serverConnect.presentation.store.ServerConnectStore

class ServerConnectComposeFragment :
    BaseComposeFragment<ServerConnectStore.Intent, ServerConnectStore.State, ServerConnectStore.Label>(
        SERVERCONNECT_VIEW_MODEL_QUALIFIER
    ) {

    override fun renderState(
        state: ServerConnectStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ServerConnectScreen(
                state = state,
                appInsets = appInsets,
                onServerAddressChanged = {
                    accept(ServerConnectStore.Intent.OnServerAddressChanged(it))
                },
                onPortChanged = {
                    accept(ServerConnectStore.Intent.OnPortChanged(it))
                }
            )
        }
    }
}