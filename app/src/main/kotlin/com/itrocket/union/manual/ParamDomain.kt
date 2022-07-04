package com.itrocket.union.manual

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class ParamDomain(
    val id: String? = null,
    val value: String,
    val type: ManualType
) : Parcelable {
    fun copy(
        id: String? = this.id,
        value: String = this.value,
        type: ManualType = this.type
    ): ParamDomain {
        return ParamDomain(id = id, value = value, type = type)
    }
}

fun List<ParamDomain>.getOrganizationId(): String? {
    return filterNotEmpty().find { it.type == ManualType.ORGANIZATION }?.id
}

fun List<ParamDomain>.getLocationIds(): List<String>? {
    return (filterNotEmpty().find { it.type == ManualType.LOCATION } as? LocationParamDomain)?.ids
}

fun List<ParamDomain>.getExploitingId(): String? {
    return filterNotEmpty().find { it.type == ManualType.EXPLOITING }?.id
}

fun List<ParamDomain>.getMolId(): String? {
    return filterNotEmpty().find { it.type == ManualType.MOL }?.id
}

fun List<ParamDomain>.getDepartmentId(): String? {
    return filterNotEmpty().find { it.type == ManualType.DEPARTMENT }?.id
}

fun List<ParamDomain>.getProducerId(): String? {
    return filterNotEmpty().find { it.type == ManualType.PRODUCER }?.id
}

fun List<ParamDomain>.getProviderId(): String? {
    return filterNotEmpty().find { it.type == ManualType.PROVIDER }?.id
}

fun List<ParamDomain>.getStatusId(): String? {
    return filterNotEmpty().find { it.type == ManualType.STATUS }?.id
}

fun List<ParamDomain>.getNomenclatureGroupId(): String? {
    return filterNotEmpty().find { it.type == ManualType.NOMENCLATURE_GROUP }?.id
}

fun List<ParamDomain>.getEquipmentTypeId(): String? {
    return filterNotEmpty().find { it.type == ManualType.EQUIPMENT_TYPE }?.id
}

fun List<ParamDomain>.getReceptionCategoryId(): String? {
    return filterNotEmpty().find { it.type == ManualType.RECEPTION_CATEGORY }?.id
}

fun List<ParamDomain>.filterNotEmpty(): List<ParamDomain> {
    return filterNot { it.id.isNullOrBlank() }
}

@Parcelize
data class LocationParamDomain(
    val ids: List<String>,
    val values: List<String>
) : Parcelable,
    ParamDomain(id = ids.toString(), value = values.joinToString(", "), type = ManualType.LOCATION)