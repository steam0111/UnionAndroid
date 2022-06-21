package com.itrocket.union.switcher.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.switcher.SwitcherModule.SWITCHER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.switcher.presentation.store.SwitcherStore

class SwitcherComposeFragment :
    BaseComposeBottomSheet<SwitcherStore.Intent, SwitcherStore.State, SwitcherStore.Label>(
        SWITCHER_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    override fun renderState(
        state: SwitcherStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            SwitcherScreen(
                state = state,
                appInsets = appInsets,
                onCrossClickListener = {
                    accept(SwitcherStore.Intent.OnCrossClicked)
                },
                onCancelClickListener = {
                    accept(SwitcherStore.Intent.OnCancelClicked)
                },
                onContinueClickListener = {
                    accept(SwitcherStore.Intent.OnContinueClicked)
                },
                onTabClickListener = {
                    accept(SwitcherStore.Intent.OnTabClicked(it))
                }
            )
        }
    }

    companion object {
        const val SWITCHER_ARG = "switcher arg"
        const val SWITCHER_RESULT = "switcher result"
        const val SWITCHER_RESULT_CODE = "switcher result code"
    }
}