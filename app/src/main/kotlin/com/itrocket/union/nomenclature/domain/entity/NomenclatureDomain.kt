package com.itrocket.union.nomenclature.domain.entity

import android.os.Parcelable
import com.itrocket.union.common.DefaultItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class NomenclatureDomain(
    val id: String,
    val name: String = "",
    val code: String?,
    val nomenclatureGroupName: String? = null

) : Parcelable

fun NomenclatureDomain.toDefaultItem() =
    DefaultItem(
        id = id,
        title = name
    )