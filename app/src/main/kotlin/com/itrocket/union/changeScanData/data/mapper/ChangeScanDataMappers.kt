package com.itrocket.union.changeScanData.data.mapper

import com.itrocket.union.changeScanData.domain.entity.ChangeScanDataDomain
import com.itrocket.union.changeScanData.domain.entity.ChangeScanType
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab

fun List<Any>.map(): List<ChangeScanDataDomain> = map {
    ChangeScanDataDomain()
}

fun ReadingModeTab.toChangeScanType() = when(this){
    ReadingModeTab.RFID -> ChangeScanType.RFID
    ReadingModeTab.BARCODE -> ChangeScanType.BARCODE
    ReadingModeTab.SN -> ChangeScanType.SN
}