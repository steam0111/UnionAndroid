package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import com.itrocket.union.manual.ManualType
import kotlinx.parcelize.Parcelize

@Parcelize
enum class DocumentTypeDomain(
    @StringRes val titleId: Int,
    val manualTypes: List<ManualType>
) :
    Parcelable {
    GIVE(
        titleId = R.string.main_issue,
        manualTypes = listOf(
            ManualType.MOL,
            ManualType.EXPLOITING,
            ManualType.LOCATION_FROM,
            ManualType.LOCATION_TO,
            ManualType.STRUCTURAL,
            ManualType.ACTION_BASE
        ),
    ),
    RETURN(
        titleId = R.string.main_return,
        manualTypes = listOf(
            ManualType.MOL,
            ManualType.EXPLOITING,
            ManualType.LOCATION_FROM,
            ManualType.LOCATION_TO,
            ManualType.STRUCTURAL,
            ManualType.ACTION_BASE
        ),
    ),
    RELOCATION(
        titleId = R.string.main_moved,
        manualTypes = listOf(
            ManualType.MOL,
            ManualType.LOCATION_FROM,
            ManualType.RELOCATION_LOCATION_TO,
            ManualType.STRUCTURAL,
            ManualType.ACTION_BASE
        ),
    ),
    WRITE_OFF(
        titleId = R.string.main_write_off,
        manualTypes = listOf(ManualType.EXPLOITING),
    ),
    COMMISSIONING(
        titleId = R.string.main_commissioning,
        manualTypes = listOf(ManualType.EXPLOITING),
    ),
    ALL(
        titleId = R.string.common_empty,
        manualTypes = listOf(ManualType.EXPLOITING),
    )
}