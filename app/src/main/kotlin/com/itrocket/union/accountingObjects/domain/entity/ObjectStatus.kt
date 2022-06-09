package com.itrocket.union.accountingObjects.domain.entity

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.itrocket.union.R
import com.itrocket.union.ui.burntSienna
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.graphite6
import com.itrocket.union.ui.green7
import com.itrocket.union.ui.violet5
import com.itrocket.union.ui.white

enum class ObjectStatus(
    override val backgroundColor: Color,
    @StringRes override val textId: Int,
    override val textColor: Color = white
): Status {
    AVAILABLE(backgroundColor = green7, textId = R.string.accounting_object_available),
    UNDER_REVIEW(backgroundColor = burntSienna, textId = R.string.accounting_object_under_review),
    UNDER_REPAIR(backgroundColor = violet5, textId = R.string.accounting_object_under_repair),
    DECOMMISSIONED(
        backgroundColor = graphite2,
        textId = R.string.accounting_object_decommissioned,
        textColor = graphite6
    ),
}