package com.itrocket.union.dataCollect.domain

import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.withContext

class DataCollectInteractor(private val coreDispatchers: CoreDispatchers) {
    suspend fun rfidsToNewList(
        rfids: List<String>,
        scanningObjects: List<String>
    ): List<String> {
        return withContext(coreDispatchers.io) {
            val newScanningList: MutableList<String> = mutableListOf()
            rfids.forEach { rfid ->
                val newScanningItem = "${ScanningObjectType.RFID.title} : $rfid"
                if (!scanningObjects.contains(newScanningItem) && rfid.isNotBlank()) {
                    newScanningList.add(newScanningItem)
                }
            }
            newScanningList.addAll(scanningObjects)
            newScanningList
        }
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