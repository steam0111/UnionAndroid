package com.itrocket.union.employees.domain.entity

data class EmployeeDomain(
    val id: String,
    val catalogItemName: String,
    val firstname: String,
    val lastname: String,
    val patronymic: String,
    val number: String,
    val nfc: String? = null
) {
    val hasNfc: Boolean
        get() = nfc != null

    val fullName: String
        get() = "$lastname $firstname $patronymic"
}