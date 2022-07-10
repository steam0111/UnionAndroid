package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import com.itrocket.union.manual.ManualType
import kotlinx.parcelize.Parcelize

@Parcelize
enum class DocumentTypeDomain(
    @StringRes val titleId: Int,
    val accountingObjectManualTypes: List<ManualType>,
    val reserveManualTypes: List<ManualType>
) :
    Parcelable {
    EXTRADITION(
        titleId = R.string.main_issue,
        accountingObjectManualTypes = listOf(ManualType.EXPLOITING),
        reserveManualTypes = listOf()
    ),
    RETURN(
        titleId = R.string.main_return,
        accountingObjectManualTypes = listOf(ManualType.EXPLOITING),
        reserveManualTypes = listOf()
    ),
    MOVING(
        titleId = R.string.main_moved,
        accountingObjectManualTypes = listOf(ManualType.LOCATION),
        reserveManualTypes = listOf(ManualType.LOCATION)
    ),
    WRITE_OFF(
        titleId = R.string.main_write_off,
        accountingObjectManualTypes = listOf(ManualType.EXPLOITING),
        reserveManualTypes = listOf()
    ),
    COMMISSIONING(
        titleId = R.string.main_commissioning,
        accountingObjectManualTypes = listOf(ManualType.EXPLOITING),
        reserveManualTypes = listOf()
    ),
    ALL(
        titleId = R.string.common_empty,
        accountingObjectManualTypes = listOf(ManualType.EXPLOITING),
        reserveManualTypes = listOf()
    )
}