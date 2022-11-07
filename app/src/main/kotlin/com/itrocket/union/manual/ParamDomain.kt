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
    open val isClickable: Boolean = true,
) : Parcelable {
    open fun copy(
        id: String? = this.id,
        value: String = this.value,
        type: ManualType = this.type,
        isFilter: Boolean = this.isFilter,
        isClickable: Boolean = this.isClickable
    ): ParamDomain {
        return ParamDomain(
            id = id,
            value = value,
            type = type,
            isFilter = isFilter,
            isClickable = isClickable
        )
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

fun List<ParamDomain>.getFilterShowUtilizedAccountingObjects(): Boolean {
    return (firstOrNull { it.type == ManualType.CHECKBOX_SHOW_UTILIZED } as? CheckBoxParamDomain)
        ?.isChecked ?: true
}

fun List<ParamDomain>.getFilterHideZeroReserves(): Boolean {
    return (firstOrNull { it.type == ManualType.CHECKBOX_HIDE_ZERO_RESERVES } as? CheckBoxParamDomain)
        ?.isChecked ?: false
}

fun List<ParamDomain>.getIndexByType(type: ManualType) = this.indexOfFirst { it.type == type }

private val listNotDefaultParams by lazy {
    listOf(ManualType.CHECKBOX_SHOW_UTILIZED, ManualType.CHECKBOX_HIDE_ZERO_RESERVES)
}

fun ParamDomain.isDefaultParamType() = !listNotDefaultParams.contains(this.type)

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
    val clickable: Boolean = true,
    val needUpdate: Boolean = true
) : Parcelable,
    ParamDomain(
        id = structurals.lastOrNull()?.id,
        value = structurals.joinToString(", ") { it.value },
        type = manualType,
        isFilter = filtered,
        isClickable = clickable
    ) {

    override fun toInitialState(): StructuralParamDomain {
        return StructuralParamDomain(manualType = manualType, needUpdate = needUpdate)
    }
}

@Parcelize
data class CheckBoxParamDomain(
    val isChecked: Boolean,
    val manualType: ManualType
) : Parcelable,
    ParamDomain(
        type = manualType,
        isFilter = false,
        isClickable = true
    ) {

    override fun toInitialState(): CheckBoxParamDomain {
        return CheckBoxParamDomain(isChecked = false, manualType = manualType)
    }
}