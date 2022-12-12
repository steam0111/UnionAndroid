package com.itrocket.sgtin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BarcodeSerialNumber(
    val barcode: String,
    val serialNumber: String
) : Parcelable