package com.itrocket.union.readingMode.presentation.view

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import kotlinx.parcelize.Parcelize
import ru.interid.scannerclient.domain.reader.ReaderMode

@Parcelize
enum class ReadingModeTab(@StringRes val textId: Int) : Parcelable {
    RFID(textId = R.string.reading_mode_rfid),
    BARCODE(textId = R.string.reading_mode_barcode),
    SN(textId = R.string.reading_mode_sn),
}

fun ReadingModeTab.toReaderMode() = when (this) {
    ReadingModeTab.RFID -> ReaderMode.RFID
    ReadingModeTab.BARCODE -> ReaderMode.BARCODE
    ReadingModeTab.SN -> ReaderMode.NONE
}