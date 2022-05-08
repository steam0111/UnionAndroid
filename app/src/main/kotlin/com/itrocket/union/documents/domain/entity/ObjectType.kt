package com.itrocket.union.documents.domain.entity

import androidx.annotation.StringRes
import com.itrocket.union.R

enum class ObjectType(@StringRes val textId: Int) {
    MAIN_ASSETS(R.string.documents_main_assets), RESERVES(R.string.documents_reserves)
}