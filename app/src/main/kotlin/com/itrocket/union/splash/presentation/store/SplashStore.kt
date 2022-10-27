package com.itrocket.union.splash.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.union.authContainer.presentation.view.AuthContainerArguments
import com.itrocket.union.splash.presentation.view.SplashComposeFragmentDirections
import com.itrocket.union.syncAll.presentation.store.SyncAllArguments
import com.itrocket.union.theme.domain.entity.Medias

interface SplashStore : Store<SplashStore.Intent, SplashStore.State, SplashStore.Label> {

    sealed class Intent

    data class State(
        val medias: Medias? = null
    )

    sealed class Label {
        object ShowAuth : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = SplashComposeFragmentDirections.toAuth(AuthContainerArguments(false))

        }

        object ShowSyncAll : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = SplashComposeFragmentDirections.toSyncAll(SyncAllArguments(false))

        }

        object ShowDocumentsMenu : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = SplashComposeFragmentDirections.toDocumentsMenu()

        }
    }
}