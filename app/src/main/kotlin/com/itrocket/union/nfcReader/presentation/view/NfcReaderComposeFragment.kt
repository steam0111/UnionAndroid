package com.itrocket.union.nfcReader.presentation.view

import android.content.Intent
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.nfc.NfcManager
import com.itrocket.union.R
import com.itrocket.union.nfcReader.NfcReaderModule.NFCREADER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.nfcReader.presentation.store.NfcReaderStore
import org.koin.android.ext.android.inject

class NfcReaderComposeFragment :
    BaseComposeBottomSheet<NfcReaderStore.Intent, NfcReaderStore.State, NfcReaderStore.Label>(
        NFCREADER_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    private val nfcManager: NfcManager by inject()

    override fun renderState(
        state: NfcReaderStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            NfcReaderScreen(
                state = state,
                appInsets = appInsets,
                onCancelClickListener = {
                    accept(NfcReaderStore.Intent.OnCancelClicked)
                },
                onOkClickListener = {
                    accept(NfcReaderStore.Intent.OnOkClicked)
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        nfcManager.enableForegroundNfcDispatch()
    }

    override fun onPause() {
        super.onPause()
        nfcManager.disableForegroundNfcDispatch()
    }

    companion object {
        const val NFC_READER_ARGUMENT = "nfc reader argument"
        const val NFC_READER_RESULT_LABEL = "nfc reader result label"
        const val NFC_READER_RESULT_CODE = "nfc reader result code"
    }
}