package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import com.itrocket.union.manual.ManualType
import kotlinx.parcelize.Parcelize

@Parcelize
enum class DocumentTypeDomain(@StringRes val titleId: Int, val manualTypes: List<ManualType>) :
    Parcelable {
    EXTRADITION(
        titleId = R.string.main_issue,
        manualTypes = listOf(ManualType.EXPLOITING)
    ),
    RETURN(
        titleId = R.string.main_return,
        manualTypes = listOf(ManualType.EXPLOITING)
    ),
    MOVING(titleId = R.string.main_moved, manualTypes = listOf(ManualType.LOCATION)),
    WRITE_OFF(titleId = R.string.main_write_off, manualTypes = listOf(ManualType.EXPLOITING)),
    COMMISSIONING(
        titleId = R.string.main_commissioning,
        manualTypes = listOf(ManualType.EXPLOITING)
    ),
    ALL(titleId = R.string.common_empty, manualTypes = listOf(ManualType.EXPLOITING))
}