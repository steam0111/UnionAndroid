package com.itrocket.union.employees.data.mapper

import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.itrocket.union.employeeDetail.data.mapper.toEmployeeStatus
import com.itrocket.union.employees.domain.entity.EmployeeDomain

fun List<EmployeeSyncEntity>.map(): List<EmployeeDomain> = map {
    EmployeeDomain(
        id = it.id,
        catalogItemName = it.catalogItemName,
        firstname = it.firstname,
        lastname = it.lastname,
        patronymic = it.patronymic,
        number = it.number,
        nfc = it.nfc,
        employeeStatus = it.statusId.toEmployeeStatus(),
        post = it.post
    )
}