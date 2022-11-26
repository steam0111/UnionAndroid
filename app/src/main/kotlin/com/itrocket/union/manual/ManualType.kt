package com.itrocket.union.manual

import androidx.annotation.StringRes
import com.itrocket.union.R

enum class ManualType(@StringRes val titleId: Int) {
    MOL(R.string.manual_mol),
    MOL_IN_STRUCTURAL(R.string.manual_mol),
    LOCATION(R.string.manual_location),
    LOCATION_INVENTORY(R.string.inventory_location),
    STRUCTURAL(R.string.manual_structural),
    STRUCTURAL_TO(R.string.manual_structural_to),
    STRUCTURAL_FROM(R.string.manual_structural_from),
    EXPLOITING(R.string.manual_exploiting),
    DATE(R.string.manual_date),
    STATUS(R.string.manual_status),
    PRODUCER(R.string.accounting_objects_producer),
    EQUIPMENT_TYPE(R.string.equipment_type),
    PROVIDER(R.string.accounting_objects_provider),
    NOMENCLATURE_GROUP(R.string.nomenclature_group),
    RECEPTION_CATEGORY(R.string.reception_category),
    LOCATION_FROM(R.string.location_from),
    LOCATION_TO(R.string.location_to),
    RELOCATION_LOCATION_TO(R.string.relocate_location_to),
    ACTION_BASE(R.string.action_base),
    INVENTORY_BASE(R.string.action_base),
    TRANSIT(R.string.transit_location),
    RECIPIENT(R.string.recipient),
    BALANCE_UNIT_FROM(R.string.balance_unit_from),
    BALANCE_UNIT_TO(R.string.balance_unit_to),
    BALANCE_UNIT(R.string.balance_unit),
    INVENTORY_CHECKER(R.string.inventory_checker_title),
    CHECKBOX_SHOW_UTILIZED(R.string.show_utilized),
    CHECKBOX_HIDE_ZERO_RESERVES(R.string.hide_zero_values),
    DOCUMENT_CODE(R.string.document_code_filter_title),
    INVENTORY_CODE(R.string.inventory_code_filter_title)
}