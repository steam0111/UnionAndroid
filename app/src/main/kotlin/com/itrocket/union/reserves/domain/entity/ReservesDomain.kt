package com.itrocket.union.reserves.domain.entity

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class ReservesDomain(
    val id: String,
    val title: String,
    val isBarcode: Boolean,
    val listInfo: List<ObjectInfoDomain>,
    val itemsCount: BigDecimal,
    val comment: String = "",
    val barcodeValue: String?,
    val nomenclatureId: String?,
    val labelTypeId: String?,
    val consignment: String?
) : Parcelable