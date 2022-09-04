package com.itrocket.union.inventoryCreate.domain.entity

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.Status
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.green7
import com.itrocket.union.ui.grey
import com.itrocket.union.ui.orange
import com.itrocket.union.ui.white

enum class InventoryAccountingObjectStatus(
    override val backgroundColor: Color,
    @StringRes override val textId: Int,
    override val textColor: Color = white
) : Status {
    FOUND(backgroundColor = green7, textId = R.string.inventory_create_found),
    NOT_FOUND(
        backgroundColor = graphite2,
        textId = R.string.inventory_create_not_found,
        textColor = grey
    ),
    NEW(backgroundColor = orange, textId = R.string.inventory_create_new),
}