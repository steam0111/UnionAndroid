package com.itrocket.union.accountingObjects.domain.entity

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.itrocket.union.R
import com.itrocket.union.ui.*
import kotlinx.parcelize.Parcelize

@Parcelize
class ObjectStatus(
    val text: String,
    val type: ObjectStatusType?
) : Parcelable

enum class ObjectStatusType(
    override val backgroundColor: Color,
    override val textColor: Color = white,
    override val textId: Int
) : Status {
    AVAILABLE(backgroundColor = green7, textId = R.string.accounting_object_available),
    GIVEN(backgroundColor = blue6, textId = R.string.accounting_object_given),
    REVIEW(backgroundColor = burntSienna, textId = R.string.accounting_object_under_review),
    REPAIR(backgroundColor = violet5, textId = R.string.accounting_object_under_repair),
    DECOMMISSIONED(
        backgroundColor = graphite2,
        textColor = graphite6,
        textId = R.string.accounting_object_decommissioned
    ),
    TRANSIT(backgroundColor = blue6, textId = R.string.accounting_object_transit)
}