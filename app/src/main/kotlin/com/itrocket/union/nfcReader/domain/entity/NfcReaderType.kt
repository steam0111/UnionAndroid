package com.itrocket.union.nfcReader.domain.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import com.itrocket.union.documents.domain.entity.DocumentDomain
import kotlinx.parcelize.Parcelize

sealed class NfcReaderType(val titleId: Int): Parcelable {
    @Parcelize
    data class DocumentConduct(val document: DocumentDomain) :
        NfcReaderType(R.string.nfc_reader_document_conduct_title)

    @Parcelize
    data class TransitConduct(val transit: DocumentDomain) :
        NfcReaderType(R.string.nfc_reader_transit_conduct_title)
}