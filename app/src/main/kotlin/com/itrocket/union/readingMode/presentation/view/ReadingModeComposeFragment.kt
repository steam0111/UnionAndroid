package com.itrocket.union.readingMode.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
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
                }
            )
        }
    }
}
