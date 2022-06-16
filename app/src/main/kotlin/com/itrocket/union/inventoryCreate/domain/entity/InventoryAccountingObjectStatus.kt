package com.itrocket.union.inventoryCreate.domain.entity

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.Status
import com.itrocket.union.ui.burntSienna
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.green7
import com.itrocket.union.ui.psb3
import com.itrocket.union.ui.psb6
import com.itrocket.union.ui.violet5
import com.itrocket.union.ui.white

enum class InventoryAccountingObjectStatus(
    override val backgroundColor: Color,
    @StringRes override val textId: Int,
    override val textColor: Color = white
): Status {
    FOUND(backgroundColor = green7, textId = R.string.inventory_create_found),
    NOT_FOUND(backgroundColor = graphite2, textId = R.string.inventory_create_not_found, textColor = psb3),
    NEW(backgroundColor = psb6, textId = R.string.inventory_create_new),
}