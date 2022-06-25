package com.itrocket.union.syncAll.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.syncAll.presentation.view.SyncAllComposeFragmentDirections

interface SyncAllStore : Store<SyncAllStore.Intent, SyncAllStore.State, SyncAllStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        object OnSyncButtonClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        object ShowMenu : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = SyncAllComposeFragmentDirections.toDocumentsMenu()
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}