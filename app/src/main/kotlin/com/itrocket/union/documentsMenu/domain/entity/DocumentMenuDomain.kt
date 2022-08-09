package com.itrocket.union.documentsMenu.domain.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission

data class DocumentMenuDomain(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
    val isEnabled: Boolean = false,
    val paddings: Int = 18,
    val unionPermission: UnionPermission = UnionPermission.NO_NEED
)