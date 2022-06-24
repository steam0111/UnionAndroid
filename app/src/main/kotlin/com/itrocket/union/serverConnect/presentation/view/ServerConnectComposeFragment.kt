package com.itrocket.union.serverConnect.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.authContainer.presentation.view.AuthContainer
import com.itrocket.union.authContainer.presentation.view.NextClickHandler
import com.itrocket.union.serverConnect.ServerConnectModule.SERVERCONNECT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.serverConnect.presentation.store.ServerConnectStore
import com.itrocket.union.utils.fragment.ChildBackPressedHandler

class ServerConnectComposeFragment :
    BaseComposeFragment<ServerConnectStore.Intent, ServerConnectStore.State, ServerConnectStore.Label>(
        SERVERCONNECT_VIEW_MODEL_QUALIFIER
    ), NextClickHandler {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (parentFragment as? ChildBackPressedHandler)?.onChildBackPressed()
            }
        }
    }

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

    override fun onNext() {
        accept(ServerConnectStore.Intent.OnNextClicked)
    }

    override fun handleLabel(label: ServerConnectStore.Label) {
        when (label) {
            ServerConnectStore.Label.NextFinish -> (parentFragment as? AuthContainer)?.onNextFinished()
            is ServerConnectStore.Label.ChangeEnable -> (parentFragment as? AuthContainer)?.isButtonEnable(
                label.enabled
            )
        }
    }
}