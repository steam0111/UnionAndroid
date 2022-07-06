package com.itrocket.union.branchDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.branchDetail.BranchDetailModule
import com.itrocket.union.branchDetail.presentation.store.BranchDetailStore

class BranchDetailComposeFragment :
    BaseComposeFragment<BranchDetailStore.Intent, BranchDetailStore.State, BranchDetailStore.Label>(
        BranchDetailModule.BRANCH_DETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<BranchDetailComposeFragmentArgs>()

    override fun renderState(
        state: BranchDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            BranchDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(BranchDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}