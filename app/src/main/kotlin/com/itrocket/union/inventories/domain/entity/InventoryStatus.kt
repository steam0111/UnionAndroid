package com.itrocket.union.inventories.domain.entity

import androidx.annotation.StringRes
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.Status
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.graphite6
import com.itrocket.union.ui.green7
import com.itrocket.union.ui.orange
import com.itrocket.union.ui.white

enum class InventoryStatus(
    override val backgroundColor: String,
    @StringRes override val textId: Int,
    override val textColor: String,
    override val text: String? = null
) : Status {
    CREATED(
        textId = R.string.inventory_created,
        backgroundColor = graphite2.value.toString(),
        textColor = graphite6.value.toString()
    ),
    IN_PROGRESS(
        textId = R.string.inventory_in_work,
        backgroundColor = orange.value.toString(),
        textColor = white.value.toString()
    ),
    COMPLETED(
        textId = R.string.inventory_done,
        backgroundColor = green7.value.toString(),
        textColor = white.value.toString()
    )
}