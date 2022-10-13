package com.itrocket.union.accountingObjects.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ObjectStatus(
    val text: String = "",
    val type: ObjectStatusType? = null
) : Parcelable

@Parcelize
class ObjectStatusType(
    val type: String,
    override val backgroundColor: String,
    override val textColor: String,
    override val text: String,
    override val textId: Int? = null
) : Parcelable, Status

enum class AccountingObjectStatus {
    AVAILABLE,
    GIVEN,
    REVIEW,
    REPAIR,
    DECOMMISSIONED,
    TRANSIT,
    WRITTEN_OFF
}