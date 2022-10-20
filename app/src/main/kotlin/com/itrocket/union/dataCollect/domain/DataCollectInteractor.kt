package com.itrocket.union.dataCollect.domain

class DataCollectInteractor {
    fun rfidsToNewList(
        rfid: String,
        scanningObjects: List<String>
    ): List<String> {
        val newScanningList: MutableList<String> = mutableListOf()
        val newScanningItem = "${ScanningObjectType.RFID.title} : $rfid"
        if (!scanningObjects.contains(newScanningItem) && rfid.isNotBlank()) {
            newScanningList.add(newScanningItem)
        }
        newScanningList.addAll(scanningObjects)
        return newScanningList
    }

    fun barcodeToNewList(
        barcode: String,
        scanningObjects: List<String>
    ): List<String> {
        val newScanningItem = "${ScanningObjectType.BARCODE.title} : $barcode"
        val newScanningList: MutableList<String> = mutableListOf()
        if (barcode.isNotEmpty()) {
            newScanningList.add(newScanningItem)
            newScanningList.addAll(listOf(newScanningItem))
        } else newScanningList.addAll(scanningObjects)
        return newScanningList
    }
}