package com.itrocket.union.manualInput.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.itrocket.union.manualInput.ManualInputModule.MANUALINPUT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.manualInput.presentation.store.ManualInputStore
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R

class ManualInputComposeFragment :
    BaseComposeBottomSheet<ManualInputStore.Intent, ManualInputStore.State, ManualInputStore.Label>(
        MANUALINPUT_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    override fun renderState(
        state: ManualInputStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ManualInputScreen(
                state = state,
                appInsets = appInsets,
                onCloseClickListener = {
                    accept(ManualInputStore.Intent.OnCloseClicked)
                },
                onTextChanged = {
                    accept(ManualInputStore.Intent.OnTextChanged(it))
                },
                onAcceptClickListener = {
                    accept(ManualInputStore.Intent.OnAcceptClicked)
                }
            )
        }
    }

    companion object {
        const val MANUAL_INPUT_ARGS = "manual input args"
        const val MANUAL_INPUT_RESULT = "manual input result"
        const val MANUAL_INPUT_CODE = "manual input code"
    }
}