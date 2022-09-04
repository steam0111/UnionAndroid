package com.itrocket.union.nfcReader.presentation.store

import android.os.Parcelable
import com.itrocket.union.nfcReader.domain.entity.NfcReaderType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NfcReaderArguments(val nfcReaderType: NfcReaderType) : Parcelable