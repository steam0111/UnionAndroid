package com.itrocket.union.bottomActionMenu.presentation.view

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import kotlinx.parcelize.Parcelize
import ru.interid.scannerclient.domain.reader.ReaderMode

@Parcelize
enum class BottomActionMenuTab(@StringRes val textId: Int) : Parcelable {
    CREATE(textId = R.string.reading_mode_rfid)
}