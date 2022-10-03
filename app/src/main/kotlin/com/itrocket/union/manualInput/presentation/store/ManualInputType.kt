package com.itrocket.union.manualInput.presentation.store

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ManualInputType(@StringRes val hintId: Int) : Parcelable {
    BARCODE(hintId = R.string.manual_input_barcode_hint), SN(hintId = R.string.manual_input_sn_hint)
}