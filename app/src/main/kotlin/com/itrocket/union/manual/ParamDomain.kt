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

fun List<ParamDomain>.getOrganizationId(): String? {
    return find { it.type == ManualType.ORGANIZATION }?.id
}

fun List<ParamDomain>.getLocationIds(): List<String>? {
    return (find { it.type == ManualType.LOCATION } as? LocationParamDomain)?.ids
}

fun List<ParamDomain>.getExploitingId(): String? {
    return find { it.type == ManualType.EXPLOITING }?.id
}

fun List<ParamDomain>.getMolId(): String? {
    return find { it.type == ManualType.MOL }?.id
}

fun List<ParamDomain>.filterNotEmpty(): List<ParamDomain> {
    return filterNot { it.id.isBlank() }
}

@Parcelize
data class LocationParamDomain(
    val ids: List<String>,
    val values: List<String>
) : Parcelable,
    ParamDomain(id = ids.toString(), value = values.joinToString(", "), type = ManualType.LOCATION)