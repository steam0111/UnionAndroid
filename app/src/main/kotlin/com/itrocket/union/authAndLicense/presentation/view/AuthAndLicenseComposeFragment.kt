package com.itrocket.union.authAndLicense.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.union.authAndLicense.AuthAndLicenseModule.AUTHANDLICENSE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.authAndLicense.presentation.store.AuthAndLicenseStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets

class AuthAndLicenseComposeFragment :
    BaseComposeFragment<AuthAndLicenseStore.Intent, AuthAndLicenseStore.State, AuthAndLicenseStore.Label>(
        AUTHANDLICENSE_VIEW_MODEL_QUALIFIER
    ) {

    override fun renderState(
        state: AuthAndLicenseStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            AuthAndLicenseScreen(
                state = state,
                appInsets = appInsets,
                onClientCodeChanged = {
                    accept(AuthAndLicenseStore.Intent.OnClientCodeChanged(it))
                },
                onNameDeviceChanged = {
                    accept(AuthAndLicenseStore.Intent.OnNameDeviceChanged(it))
                },
                onSecurityCodeChanged = {
                    accept(AuthAndLicenseStore.Intent.OnSecurityCodeChanged(it))
                }
            )
        }
    }
}