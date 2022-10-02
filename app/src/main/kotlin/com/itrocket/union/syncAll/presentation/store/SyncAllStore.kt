package com.itrocket.union.syncAll.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.example.union_sync_api.entity.SyncEvent
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.authContainer.presentation.view.AuthContainerArguments
import com.itrocket.union.syncAll.presentation.view.SyncAllComposeFragmentDirections

interface SyncAllStore : Store<SyncAllStore.Intent, SyncAllStore.State, SyncAllStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        object OnSyncButtonClicked : Intent()
        object OnClearButtonClicked : Intent()
        object OnAuthButtonClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val syncEvents: List<SyncEvent> = mutableListOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        object ShowMenu : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = SyncAllComposeFragmentDirections.toDocumentsMenu()
        }

        object ShowAuth : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = SyncAllComposeFragmentDirections.toAuth(AuthContainerArguments(true))
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}