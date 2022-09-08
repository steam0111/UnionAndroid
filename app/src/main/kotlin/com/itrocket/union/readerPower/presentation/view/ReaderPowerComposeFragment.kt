package com.itrocket.union.readerPower.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.readerPower.ReaderPowerModule.READERPOWER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.readerPower.presentation.store.ReaderPowerStore

class ReaderPowerComposeFragment :
    BaseComposeBottomSheet<ReaderPowerStore.Intent, ReaderPowerStore.State, ReaderPowerStore.Label>(
        READERPOWER_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    override fun renderState(
        state: ReaderPowerStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ReaderPowerScreen(
                state = state,
                appInsets = appInsets,
                onCrossClickListener = { accept(ReaderPowerStore.Intent.OnCrossClicked) },
                onCancelClickListener = { accept(ReaderPowerStore.Intent.OnCancelClicked) },
                onAcceptClickListener = { accept(ReaderPowerStore.Intent.OnAcceptClicked) },
                onPowerChanged = { accept(ReaderPowerStore.Intent.OnPowerChanged(it)) },
                onArrowUpClickListener = { accept(ReaderPowerStore.Intent.OnArrowUpClicked) },
                onArrowDownClickListener = { accept(ReaderPowerStore.Intent.OnArrowDownClicked) }
            )
        }
    }

    companion object {
        const val READER_POWER_RESULT_CODE = "reader power result code"
        const val READER_POWER_RESULT = "reader power result"
    }
}