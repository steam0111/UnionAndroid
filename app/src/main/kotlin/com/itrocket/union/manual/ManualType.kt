package com.itrocket.union.manual

import androidx.annotation.StringRes
import com.itrocket.union.R

enum class ManualType(@StringRes val titleId: Int) {
    ORGANIZATION(R.string.manual_organization),
    MOL(R.string.manual_mol),
    LOCATION(R.string.manual_location)
}