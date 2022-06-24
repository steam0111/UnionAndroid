package com.itrocket.union.employeeDetail.data.mapper

import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.employees.domain.entity.EmployeeStatus

fun EmployeeDetailSyncEntity.toEmployeeDetailDomain(): EmployeeDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    listInfo.add(ObjectInfoDomain(R.string.employees_firstname, employee.firstname))
    listInfo.add(ObjectInfoDomain(R.string.employees_lastname, employee.lastname))
    listInfo.add(ObjectInfoDomain(R.string.employees_patronymic, employee.patronymic))
    listInfo.add(ObjectInfoDomain(R.string.employees_number, employee.number))
    employee.post?.let {
        listInfo.add(ObjectInfoDomain(R.string.employees_post, it))
    }
    employee.statusId?.toEmployeeStatus()?.let {
        listInfo.add(ObjectInfoDomain(R.string.employees_status, valueRes = it.titleId))
    }
    employee.nfc?.let {
        listInfo.add(ObjectInfoDomain(R.string.employees_nfc, it))
    }
    organizationSyncEntity?.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.organization, it))
    }
    return EmployeeDetailDomain(listInfo)
}

fun String?.toEmployeeStatus() = EmployeeStatus.values().firstOrNull { it.slug == this }