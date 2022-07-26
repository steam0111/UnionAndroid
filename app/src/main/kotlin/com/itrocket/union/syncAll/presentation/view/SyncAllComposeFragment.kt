package com.itrocket.union.syncAll.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.syncAll.SyncAllModule.SYNCALL_VIEW_MODEL_QUALIFIER
import com.itrocket.union.syncAll.presentation.store.SyncAllStore

class SyncAllComposeFragment :
    BaseComposeFragment<SyncAllStore.Intent, SyncAllStore.State, SyncAllStore.Label>(
        SYNCALL_VIEW_MODEL_QUALIFIER
    ) {
    override fun renderState(
        state: SyncAllStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            SyncAllScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(SyncAllStore.Intent.OnBackClicked)
                },
                onSyncButtonClicked = {
                    accept(SyncAllStore.Intent.OnSyncButtonClicked)
                },
                onClearButtonClicked = {
                    accept(SyncAllStore.Intent.OnClearButtonClicked)
                },
                onAuthButtonClicked = {
                    accept((SyncAllStore.Intent.OnAuthButtonClicked))
                }
            )
        }
    }
}