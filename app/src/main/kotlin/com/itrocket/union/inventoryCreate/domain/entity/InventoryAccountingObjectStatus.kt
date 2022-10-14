package com.itrocket.union.inventoryCreate.domain.entity

import androidx.annotation.StringRes
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.Status
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.green7
import com.itrocket.union.ui.grey
import com.itrocket.union.ui.orange
import com.itrocket.union.ui.white

enum class InventoryAccountingObjectStatus(
    override val backgroundColor: String,
    @StringRes override val textId: Int,
    override val textColor: String = white.value.toString(),
    override val text: String? = null
) : Status {
    FOUND(backgroundColor = green7.value.toString(), textId = R.string.inventory_create_found),
    NOT_FOUND(
        backgroundColor = graphite2.value.toString(),
        textId = R.string.inventory_create_not_found,
        textColor = grey.value.toString()
    ),
    NEW(backgroundColor = orange.value.toString(), textId = R.string.inventory_create_new),
}