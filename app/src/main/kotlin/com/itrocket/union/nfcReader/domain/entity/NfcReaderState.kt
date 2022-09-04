package com.itrocket.union.nfcReader.domain.entity

import androidx.annotation.StringRes

sealed class NfcReaderState {
    object Waiting : NfcReaderState()
    object Success : NfcReaderState()
    data class Error(@StringRes val errorId: Int) : NfcReaderState()
}