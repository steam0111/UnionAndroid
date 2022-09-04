package com.itrocket.union.nfcReader.presentation.store

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NfcReaderResult(val isNfcReadingSuccess: Boolean, val documentId: String? = null) : Parcelable