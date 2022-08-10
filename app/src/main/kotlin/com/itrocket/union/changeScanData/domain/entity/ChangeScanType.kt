package com.itrocket.union.changeScanData.domain.entity

import androidx.annotation.StringRes
import com.itrocket.union.R

enum class ChangeScanType(
    @StringRes val titleId: Int,
    @StringRes val fromChangeId: Int,
    @StringRes val toChangeId: Int,
    @StringRes val emptyValueId: Int
) {
    RFID(
        titleId = R.string.change_scan_data_rfid_title,
        fromChangeId = R.string.change_scan_data_with_rfid,
        toChangeId = R.string.change_scan_data_on_rfid,
        emptyValueId = R.string.change_scan_data_empty_rfid,
    ),
    BARCODE(
        titleId = R.string.change_scan_data_barcode_title,
        fromChangeId = R.string.change_scan_data_with_barcode,
        toChangeId = R.string.change_scan_data_on_barcode,
        emptyValueId = R.string.change_scan_data_empty_barcode,
    ),
    SN(
        titleId = R.string.change_scan_data_sn_title,
        fromChangeId = R.string.change_scan_data_with_sn,
        toChangeId = R.string.change_scan_data_on_sn,
        emptyValueId = R.string.change_scan_data_empty_sn,
    )
}