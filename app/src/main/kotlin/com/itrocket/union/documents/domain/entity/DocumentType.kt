package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import com.itrocket.union.manual.ManualType
import kotlinx.parcelize.Parcelize

@Parcelize
enum class DocumentTypeDomain(@StringRes val titleId: Int, val manualType: ManualType) : Parcelable {
    EXTRADITION(titleId = R.string.main_issue, manualType = ManualType.EXPLOITING),
    RETURN(titleId = R.string.main_return, manualType = ManualType.EXPLOITING),
    MOVING(titleId = R.string.main_moved, manualType = ManualType.LOCATION),
    WRITE_OFF(titleId = R.string.main_write_off, manualType = ManualType.EXPLOITING),
    COMMISSIONING(titleId = R.string.main_commissioning, manualType = ManualType.EXPLOITING),
    ALL(titleId = R.string.common_empty, manualType = ManualType.EXPLOITING)
}