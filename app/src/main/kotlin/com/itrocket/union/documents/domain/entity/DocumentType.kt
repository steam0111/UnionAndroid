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
            ManualType.EXPLOITING,
            ManualType.LOCATION_FROM,
            ManualType.LOCATION_TO,
            ManualType.STRUCTURAL_FROM,
            ManualType.STRUCTURAL_TO,
            ManualType.ACTION_BASE,
            ManualType.BALANCE_UNIT_FROM,
            ManualType.BALANCE_UNIT_TO
        ),
    ),
    RETURN(
        titleId = R.string.main_return,
        manualTypes = listOf(
            ManualType.LOCATION_FROM,
            ManualType.LOCATION_TO,
            ManualType.STRUCTURAL_FROM,
            ManualType.STRUCTURAL_TO,
            ManualType.ACTION_BASE,
            ManualType.BALANCE_UNIT_FROM,
            ManualType.BALANCE_UNIT_TO
        ),
    ),
    RELOCATION(
        titleId = R.string.main_moved,
        manualTypes = listOf(
            ManualType.MOL_IN_STRUCTURAL,
            ManualType.LOCATION_FROM,
            ManualType.RELOCATION_LOCATION_TO,
            ManualType.STRUCTURAL_FROM,
            ManualType.STRUCTURAL_TO,
            ManualType.ACTION_BASE,
            ManualType.BALANCE_UNIT_FROM,
            ManualType.BALANCE_UNIT_TO
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
    TRANSIT(
        titleId = R.string.main_transit,
        manualTypes = listOf(
            ManualType.STRUCTURAL_FROM,
            ManualType.STRUCTURAL_TO,
            ManualType.RECIPIENT,
            ManualType.MOL,
            ManualType.LOCATION_FROM,
            ManualType.TRANSIT,
            ManualType.LOCATION_TO,
        )
    ),
    ALL(
        titleId = R.string.common_empty,
        manualTypes = listOf(ManualType.EXPLOITING),
    )
}