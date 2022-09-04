package com.itrocket.union.nfcReader.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.nfcReader.domain.entity.NfcReaderState
import com.itrocket.union.nfcReader.domain.entity.NfcReaderType
import com.itrocket.union.nfcReader.presentation.view.NfcReaderComposeFragment

interface NfcReaderStore :
    Store<NfcReaderStore.Intent, NfcReaderStore.State, NfcReaderStore.Label> {

    sealed class Intent {
        object OnOkClicked : Intent()
        object OnCancelClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val nfcReaderType: NfcReaderType,
        val nfcReaderState: NfcReaderState = NfcReaderState.Waiting,
        val documentId: String? = null
    )

    sealed class Label {
        data class GoBack(override val result: NfcReaderResult) : Label(), GoBackNavigationLabel {
            override val resultCode: String
                get() = NfcReaderComposeFragment.NFC_READER_RESULT_CODE

            override val resultLabel: String
                get() = NfcReaderComposeFragment.NFC_READER_RESULT_LABEL
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}