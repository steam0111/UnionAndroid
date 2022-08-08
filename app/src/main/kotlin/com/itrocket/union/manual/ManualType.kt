package com.itrocket.union.manual

import androidx.annotation.StringRes
import com.itrocket.union.R

enum class ManualType(@StringRes val titleId: Int) {
    MOL(R.string.manual_mol),
    LOCATION(R.string.manual_location),
    STRUCTURAL(R.string.manual_structural),
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
    ACTION_BASE(R.string.action_base)
}