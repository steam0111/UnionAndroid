package com.itrocket.union.selectCount.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.selectCount.SelectCountModule.SELECTCOUNT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.selectCount.presentation.store.SelectCountStore

class SelectCountComposeFragment :
    BaseComposeBottomSheet<SelectCountStore.Intent, SelectCountStore.State, SelectCountStore.Label>(
        SELECTCOUNT_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    override fun renderState(
        state: SelectCountStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            SelectCountScreen(
                state = state,
                appInsets = appInsets,
                onAcceptClickListener = {
                    accept(SelectCountStore.Intent.OnAcceptClicked)
                },
                onCountChanged = {
                    accept(SelectCountStore.Intent.OnCountChanged(it))
                }
            )
        }
    }

    companion object {
        const val SELECT_COUNT_ARG = "select count arg"
        const val SELECT_COUNT_RESULT_CODE = "select count result code"
        const val SELECT_COUNT_RESULT_LABEL = "select count result label"
    }
}