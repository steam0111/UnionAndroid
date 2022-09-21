package com.itrocket.union.readingMode.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.readingMode.ReadingModeModule.READINGMODE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.readingMode.presentation.store.ReadingModeStore

class ReadingModeComposeFragment :
    BaseComposeBottomSheet<ReadingModeStore.Intent, ReadingModeStore.State, ReadingModeStore.Label>(
        READINGMODE_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    override fun renderState(
        state: ReadingModeStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ReadingModeScreen(
                state = state,
                appInsets = appInsets,
                onCameraClickListener = {
                    accept(ReadingModeStore.Intent.OnCameraClicked)
                },
                onManualInputClickListener = {
                    accept(ReadingModeStore.Intent.OnManualInputClicked)
                },
                onSettingsClickListener = {
                    accept(ReadingModeStore.Intent.OnSettingsClicked)
                },
                onReadingModeTabClickListener = {
                    accept(ReadingModeStore.Intent.OnReadingModeSelected(it))
                },
                onReaderPowerClickListener = {
                    accept(ReadingModeStore.Intent.OnReaderPowerClicked(it))
                }
            )
        }
    }

    override fun handleLabel(label: ReadingModeStore.Label) {
        if (label is ReadingModeStore.Label.ResultReadingTab) {
            setFragmentResult(
                READING_MODE_TAB_RESULT_CODE,
                bundleOf(READING_MODE_TAB_RESULT_LABEL to label.readingMode)
            )
        } else {
            super.handleLabel(label)
        }
    }

    companion object {
        const val READING_MODE_TAB_RESULT_LABEL = "reading mode tab result label"
        const val READING_MODE_TAB_RESULT_CODE = "reading mode tab result code"
    }
}
