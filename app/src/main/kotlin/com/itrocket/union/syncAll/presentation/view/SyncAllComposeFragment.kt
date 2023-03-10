package com.itrocket.union.syncAll.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.syncAll.SyncAllModule.SYNCALL_VIEW_MODEL_QUALIFIER
import com.itrocket.union.syncAll.presentation.store.SyncAllStore

class SyncAllComposeFragment :
    BaseComposeFragment<SyncAllStore.Intent, SyncAllStore.State, SyncAllStore.Label>(
        SYNCALL_VIEW_MODEL_QUALIFIER
    ) {

    override val navArgs by navArgs<SyncAllComposeFragmentArgs>()

    override fun renderState(
        state: SyncAllStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            SyncAllScreen(
                state = state,
                appInsets = appInsets,
                onSyncClickListener = {
                    accept(SyncAllStore.Intent.OnSyncButtonClicked)
                },
                onCrossClickListener = {
                    accept(SyncAllStore.Intent.OnBackClicked)
                },
                onChangeVisibleLogClickListener = {
                    accept(SyncAllStore.Intent.OnChangeLogVisibilityClicked)
                },
                onFinishClickListener = {
                    accept(SyncAllStore.Intent.OnFinishClicked)
                },
            )
        }
    }
}