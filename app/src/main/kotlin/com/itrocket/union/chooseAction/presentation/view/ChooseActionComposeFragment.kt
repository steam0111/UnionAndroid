package com.itrocket.union.chooseAction.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.chooseAction.ChooseActionModule.CHOOSEACTION_VIEW_MODEL_QUALIFIER
import com.itrocket.union.chooseAction.presentation.store.ChooseActionStore

class ChooseActionComposeFragment :
    BaseComposeBottomSheet<ChooseActionStore.Intent, ChooseActionStore.State, ChooseActionStore.Label>(
        CHOOSEACTION_VIEW_MODEL_QUALIFIER
    ) {
    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    override fun renderState(
        state: ChooseActionStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ChooseActionScreen(
                state = state,
                appInsets = appInsets,
                onTypeClickListener = {
                    accept(ChooseActionStore.Intent.OnTypeClicked(it))
                }
            )
        }
    }

    companion object {
        const val CHOOSE_ACTION_RESULT_CODE = "choose action result code"
        const val CHOOSE_ACTION_RESULT_LABEL = "choose action result label"
    }
}