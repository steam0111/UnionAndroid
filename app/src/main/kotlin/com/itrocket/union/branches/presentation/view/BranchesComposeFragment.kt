package com.itrocket.union.branches.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.branches.BranchesModule.BRANCHES_VIEW_MODEL_QUALIFIER
import com.itrocket.union.branches.presentation.store.BranchesStore

class BranchesComposeFragment :
    BaseComposeFragment<BranchesStore.Intent, BranchesStore.State, BranchesStore.Label>(
        BRANCHES_VIEW_MODEL_QUALIFIER
    ) {

    override fun renderState(
        state: BranchesStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            BranchesScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = { accept(BranchesStore.Intent.OnBackClicked) },
                onBranchClickListener = {}
            )
        }
    }
}