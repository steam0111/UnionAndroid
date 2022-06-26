package com.itrocket.union.manual

import androidx.annotation.StringRes
import com.itrocket.union.R

enum class ManualType(@StringRes val titleId: Int) {
    ORGANIZATION(R.string.manual_organization),
    MOL(R.string.manual_mol),
    LOCATION(R.string.manual_location),
    EXPLOITING(R.string.manual_exploiting),
    DATE(R.string.manual_date),
    STATUS(R.string.manual_status),
    DEPARTMENT(R.string.department_title),
    PRODUCER(R.string.accounting_objects_producer),
    EQUIPMENT_TYPE(R.string.equipment_type),
    PROVIDER(R.string.accounting_objects_provider),
    NOMENCLATURE_GROUP(R.string.nomenclature_group)
}