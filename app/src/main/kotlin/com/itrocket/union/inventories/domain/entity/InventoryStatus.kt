package com.itrocket.union.inventories.domain.entity

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.Status
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.graphite6
import com.itrocket.union.ui.green7
import com.itrocket.union.ui.orange
import com.itrocket.union.ui.white

enum class InventoryStatus(
    override val backgroundColor: Color,
    @StringRes override val textId: Int,
    override val textColor: Color
) : Status {
    CREATED(
        textId = R.string.inventory_created,
        backgroundColor = graphite2,
        textColor = graphite6
    ),
    IN_PROGRESS(
        textId = R.string.inventory_in_work,
        backgroundColor = orange,
        textColor = white
    ),
    COMPLETED(
        textId = R.string.inventory_done,
        backgroundColor = green7,
        textColor = white
    )
}