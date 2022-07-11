package com.itrocket.union.manual

import android.os.Parcelable
import com.itrocket.union.location.domain.entity.LocationDomain
import kotlinx.parcelize.Parcelize

@Parcelize
open class ParamDomain(
    val id: String? = null,
    val value: String = "",
    val type: ManualType,
    val isFilter: Boolean = true
) : Parcelable {
    fun copy(
        id: String? = this.id,
        value: String = this.value,
        type: ManualType = this.type,
        isFilter: Boolean = this.isFilter
    ): ParamDomain {
        return ParamDomain(id = id, value = value, type = type, isFilter = isFilter)
    }

    open fun toInitialState() = ParamDomain(type = type)
}

fun List<ParamDomain>.getOrganizationId(): String? {
    return filterNotEmpty().find { it.type == ManualType.ORGANIZATION }?.id
}

fun List<ParamDomain>.getFilterLocationLastId(): String? {
    return getLocationParamDomain()?.locations?.lastOrNull()?.id
}

fun List<ParamDomain>.getFilterLocationIds(): List<String>? {
    return getLocationParamDomain()?.locations?.map { it.id }
}

fun List<ParamDomain>.getLocationParamDomain(): LocationParamDomain? {
    return (filterNotEmpty().find { it.type == ManualType.LOCATION } as? LocationParamDomain)
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
    val filtered: Boolean = true,
    val locations: List<LocationDomain> = emptyList()
) : Parcelable,
    ParamDomain(
        id = locations.lastOrNull()?.id.toString(),
        value = locations.joinToString(", ") { it.value },
        type = ManualType.LOCATION,
        isFilter = filtered
    ) {

    override fun toInitialState(): LocationParamDomain {
        return LocationParamDomain()
    }
}