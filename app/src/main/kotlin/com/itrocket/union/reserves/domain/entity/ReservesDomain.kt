package com.itrocket.union.reserves.domain.entity

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.identify.domain.entity.OSandReserves
import java.math.BigDecimal
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservesDomain(
    override val id: String,
    val title: String,
    val isBarcode: Boolean,
    val listInfo: List<ObjectInfoDomain>,
    val itemsCount: Long,
    val comment: String = ""
) : Parcelable, OSandReserves(id)