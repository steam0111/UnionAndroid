package com.itrocket.union.accountingObjectDetail.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class EmployeeDetailDomain(
    val id: String,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val nfc: String?,
    val listInfo: List<ObjectInfoDomain> = emptyList()
) {
    val fullName: String
        get() = "${lastName.trim()} ${firstName.trim()} ${patronymic.trim()}"
}