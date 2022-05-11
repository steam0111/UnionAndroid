package com.itrocket.union.authAndLicense.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store

interface AuthAndLicenseStore :
    Store<AuthAndLicenseStore.Intent, AuthAndLicenseStore.State, AuthAndLicenseStore.Label> {

    sealed class Intent {
        data class OnClientCodeChanged(val clientCode: String) : Intent()
        data class OnNameDeviceChanged(val deviceName: String) : Intent()
        data class OnSecurityCodeChanged(val securityCode: String) : Intent()
    }

    data class State(
        val clientCode: String = "",
        val deviceName: String = "",
        val securityCode: String = ""
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
    }
}