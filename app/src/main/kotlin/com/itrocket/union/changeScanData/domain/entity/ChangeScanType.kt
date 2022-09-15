package com.itrocket.union.changeScanData.domain.entity

import androidx.annotation.StringRes
import com.itrocket.union.R

enum class ChangeScanType(
    @StringRes val titleId: Int,
    @StringRes val fromChangeId: Int,
    @StringRes val toChangeId: Int,
    @StringRes val emptyValueId: Int,
    @StringRes val hintId: Int,
    @StringRes val successLabelId: Int,
    @StringRes val errorLabelId: Int
) {
    RFID(
        titleId = R.string.change_scan_data_rfid_title,
        fromChangeId = R.string.change_scan_data_with_rfid,
        toChangeId = R.string.change_scan_data_on_rfid,
        emptyValueId = R.string.change_scan_data_empty_rfid,
        hintId = 0,
        successLabelId = R.string.change_scan_data_success_rfid,
        errorLabelId = R.string.change_scan_data_error_rfid
    ),
    BARCODE(
        titleId = R.string.change_scan_data_barcode_title,
        fromChangeId = R.string.change_scan_data_with_barcode,
        toChangeId = R.string.change_scan_data_on_barcode,
        emptyValueId = R.string.change_scan_data_empty_barcode,
        hintId = R.string.change_scan_data_hint_barcode,
        successLabelId = R.string.change_scan_data_success_barcode,
        errorLabelId = R.string.change_scan_data_error_barcode
    ),
    SN(
        titleId = R.string.change_scan_data_sn_title,
        fromChangeId = R.string.change_scan_data_with_sn,
        toChangeId = R.string.change_scan_data_on_sn,
        emptyValueId = R.string.change_scan_data_empty_sn,
        hintId = R.string.change_scan_data_hint_sn,
        successLabelId = R.string.change_scan_data_success_sn,
        errorLabelId = R.string.change_scan_data_error_sn
    )
}