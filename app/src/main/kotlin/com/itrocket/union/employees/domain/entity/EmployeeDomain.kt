package com.itrocket.union.employees.domain.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import com.itrocket.union.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeDomain(
    val id: String,
    val catalogItemName: String,
    val firstname: String,
    val lastname: String,
    val patronymic: String,
    val number: String,
    val nfc: String? = null,
    val post: String?,
    val employeeStatus: EmployeeStatus?
) : Parcelable {
    val hasNfc: Boolean
        get() = nfc != null

    val fullName: String
        get() = "$lastname $firstname $patronymic"
}

enum class EmployeeStatus(val slug: String, @StringRes val titleId: Int) {
    WITHOUT_ORGANIZATION("WITHOUT_ORGANIZATION", R.string.employee_status_without_organization),
    MOL("MOL", R.string.employee_status_mol),
    NATURAL_PERSON("NATURAL_PERSON", R.string.employee_status_without_organization),
    INTER_ID("InterID", R.string.employee_status_mol)
}