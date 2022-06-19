package com.itrocket.union.manual

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class ParamDomain(
    val id: String,
    val value: String,
    val type: ManualType
) : Parcelable {
    fun copy(
        id: String = this.id,
        value: String = this.value,
        type: ManualType = this.type
    ): ParamDomain {
        return ParamDomain(id = id, value = value, type = type)
    }
}

@Parcelize
data class LocationParamDomain(
    val ids: List<String>,
    val values: List<String>
) : Parcelable,
    ParamDomain(id = ids.toString(), value = values.joinToString(", "), type = ManualType.LOCATION)