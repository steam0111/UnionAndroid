package com.itrocket.union.manual

import android.os.Parcelable
import com.itrocket.union.location.domain.entity.LocationDomain
import com.itrocket.union.structural.domain.entity.StructuralDomain
import kotlinx.parcelize.Parcelize

@Parcelize
open class ParamDomain(
    open val id: String? = null,
    open val value: String = "",
    open val type: ManualType,
    open val isFilter: Boolean = true,
    open val isClickable: Boolean = true
) : Parcelable {
    open fun copy(
        id: String? = this.id,
        value: String = this.value,
        type: ManualType = this.type,
        isFilter: Boolean = this.isFilter
    ): ParamDomain {
        return ParamDomain(id = id, value = value, type = type, isFilter = isFilter)
    }

    open fun toInitialState() = ParamDomain(type = type)
}


fun List<ParamDomain>.getFilterStructuralLastId(type: ManualType): String? {
    return getStructuralParamDomain(type)?.structurals?.lastOrNull()?.id
}

fun List<ParamDomain>.getFilterLocationLastId(type: ManualType = ManualType.LOCATION): String? {
    return getLocationParamDomain(type)?.locations?.lastOrNull()?.id
}

fun List<ParamDomain>.getFilterLocationIds(type: ManualType = ManualType.LOCATION): List<String>? {
    return getLocationParamDomain(type)?.locations?.map { it.id }
}

fun List<ParamDomain>.getFilterInventoryBaseId(): String? {
    return filterNotEmpty().find { it.type == ManualType.INVENTORY_BASE }?.id
}

fun List<ParamDomain>.getStructuralParamDomain(type: ManualType): StructuralParamDomain? {
    return (filterNotEmpty().find { it.type == type } as? StructuralParamDomain)
}

fun List<ParamDomain>.getLocationParamDomain(type: ManualType): LocationParamDomain? {
    return (filterNotEmpty().find { it.type == type } as? LocationParamDomain)
}

fun List<ParamDomain>.getExploitingId(): String? {
    return filterNotEmpty().find { it.type == ManualType.EXPLOITING }?.id
}

fun List<ParamDomain>.getMolId(): String? {
    return filterNotEmpty().find { it.type == ManualType.MOL }?.id
}

fun List<ParamDomain>.getMolInDepartmentId(): String? {
    return filterNotEmpty().find { it.type == ManualType.MOL_IN_STRUCTURAL }?.id
}

fun List<ParamDomain>.getActionBaseId(): String? {
    return filterNotEmpty().find { it.type == ManualType.ACTION_BASE }?.id
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

fun List<ParamDomain>.getReceivingId(): String? {
    return filterNotEmpty().find { it.type == ManualType.RECIPIENT }?.id
}

fun List<ParamDomain>.filterNotEmpty(): List<ParamDomain> {
    return filterNot { it.id.isNullOrBlank() }
}

@Parcelize
data class LocationParamDomain(
    val filtered: Boolean = true,
    val locations: List<LocationDomain> = emptyList(),
    val manualType: ManualType = ManualType.LOCATION
) : Parcelable,
    ParamDomain(
        id = locations.lastOrNull()?.id.toString(),
        value = locations.joinToString(", ") { it.value },
        type = manualType,
        isFilter = filtered
    ) {

    override fun toInitialState(): LocationParamDomain {
        return LocationParamDomain(manualType = manualType)
    }
}

@Parcelize
data class StructuralParamDomain(
    val filtered: Boolean = true,
    val structurals: List<StructuralDomain> = emptyList(),
    val manualType: ManualType,
    val clickable: Boolean = true
) : Parcelable,
    ParamDomain(
        id = structurals.lastOrNull()?.id,
        value = structurals.joinToString(", ") { it.value },
        type = manualType,
        isFilter = filtered,
        isClickable = clickable
    ) {

    override fun toInitialState(): StructuralParamDomain {
        return StructuralParamDomain(manualType = manualType)
    }
}