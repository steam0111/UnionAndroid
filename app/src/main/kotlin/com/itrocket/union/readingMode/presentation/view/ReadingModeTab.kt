package com.itrocket.union.readingMode.presentation.view

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import kotlinx.parcelize.Parcelize
import ru.interid.scannerclient_impl.platform.entry.ReadingMode

@Parcelize
enum class ReadingModeTab(@StringRes val textId: Int) : Parcelable {
    RFID(textId = R.string.reading_mode_rfid),
    BARCODE(textId = R.string.reading_mode_barcode),
    SN(textId = R.string.reading_mode_sn),
}

fun ReadingModeTab.toReadingMode() = when (this) {
    ReadingModeTab.RFID -> ReadingMode.RFID
    ReadingModeTab.BARCODE -> ReadingMode.BARCODE
    ReadingModeTab.SN -> ReadingMode.SN
}

fun ReadingMode.toReadingModeTab() = when (this) {
    ReadingMode.BARCODE -> ReadingModeTab.BARCODE
    ReadingMode.RFID -> ReadingModeTab.RFID
    ReadingMode.SN -> ReadingModeTab.SN
    ReadingMode.NONE -> ReadingModeTab.RFID
}