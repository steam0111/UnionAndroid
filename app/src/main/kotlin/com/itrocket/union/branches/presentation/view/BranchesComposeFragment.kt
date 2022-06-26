package com.itrocket.union.branches.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.branches.BranchesModule.BRANCHES_VIEW_MODEL_QUALIFIER
import com.itrocket.union.branches.presentation.store.BranchesStore
import com.itrocket.union.filter.presentation.view.FilterComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult

class BranchesComposeFragment :
    BaseComposeFragment<BranchesStore.Intent, BranchesStore.State, BranchesStore.Label>(
        BRANCHES_VIEW_MODEL_QUALIFIER
    ) {

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = FilterComposeFragment.FILTER_RESULT_CODE,
                resultLabel = FilterComposeFragment.FILTER_RESULT_LABEL,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(BranchesStore.Intent.OnFilterResult(it))
                    }
                }
            )
        )

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
                onBranchClickListener = { accept(BranchesStore.Intent.OnBranchClicked(it.id)) },
                onFilterClickListener = { accept(BranchesStore.Intent.OnFilterClicked) }
            )
        }
    }
}