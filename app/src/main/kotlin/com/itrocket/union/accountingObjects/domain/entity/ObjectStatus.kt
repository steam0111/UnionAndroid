package com.itrocket.union.accountingObjects.domain.entity

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.example.union_sync_api.entity.EnumSyncEntity
import com.itrocket.union.R
import com.itrocket.union.ui.*
import kotlinx.parcelize.Parcelize

@Parcelize
class ObjectStatus(
    val text: String = "",
    val type: ObjectStatusType? = null
) : Parcelable

@Parcelize
class ObjectStatusType(
    val type: String,
    override val backgroundColor: Color,
    override val textColor: Color,
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