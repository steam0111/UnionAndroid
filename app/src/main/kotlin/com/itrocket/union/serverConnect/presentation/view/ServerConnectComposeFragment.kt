package com.itrocket.union.serverConnect.presentation.view

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.authContainer.presentation.view.AuthContainer
import com.itrocket.union.authContainer.presentation.view.NextClickHandler
import com.itrocket.union.serverConnect.ServerConnectModule.SERVERCONNECT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.serverConnect.presentation.contract.CreateFileContract
import com.itrocket.union.serverConnect.presentation.contract.OpenFileContract
import com.itrocket.union.serverConnect.presentation.store.ServerConnectStore
import com.itrocket.union.uniqueDeviceId.UniqueDeviceIdModule.UNIQUE_DEVICE_ID_EXTERNAL_FILE_NAME
import com.itrocket.union.utils.fragment.ChildBackPressedHandler

class ServerConnectComposeFragment :
    BaseComposeFragment<ServerConnectStore.Intent, ServerConnectStore.State, ServerConnectStore.Label>(
        SERVERCONNECT_VIEW_MODEL_QUALIFIER
    ), NextClickHandler {

    private var createFileContract: ActivityResultLauncher<String>? = null
    private var openFileContract: ActivityResultLauncher<String>? = null

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (parentFragment as? ChildBackPressedHandler)?.onChildBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createFileContract = requireActivity().activityResultRegistry.register(
            "CreateAppDataFile",
            this,
            CreateFileContract()
        ) { uri ->
            if (uri != null) {
                accept(ServerConnectStore.Intent.OnFileCreated(uri))
            }
        }

        openFileContract = requireActivity().activityResultRegistry.register(
            "OpenAppDataFile",
            this,
            OpenFileContract()
        ) { uri ->
            if (uri != null) {
                accept(ServerConnectStore.Intent.OnFileOpened(uri))
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
                },
                onCreateFileClick = {
                    accept(ServerConnectStore.Intent.OnCreateFileClick)
                },
                onOpenFileClick = {
                    accept(ServerConnectStore.Intent.OnOpenFileClick)
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
            is ServerConnectStore.Label.ParentLoading -> (parentFragment as? AuthContainer)?.isLoading(
                label.isLoading
            )
            is ServerConnectStore.Label.CreateAppDataFile -> {
                createFileContract?.launch(UNIQUE_DEVICE_ID_EXTERNAL_FILE_NAME)
            }
            is ServerConnectStore.Label.OpenAppDataFile -> {
                openFileContract?.launch("application/*")
            }
        }
    }
}