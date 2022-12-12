package com.itrocket.sgtin

interface SgtinFormatter {

    suspend fun barcodeToEpcRfid(barcode: String, serialNumber: String): String

    suspend fun epcRfidToBarcode(epcRfid: String) : BarcodeSerialNumber
}