package com.itrocket.union.documentsMenu.domain.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class DocumentMenuDomain(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
    val isEnabled: Boolean = false,
    val paddings: Int = 18
)