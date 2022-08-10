package com.itrocket.union.employeeDetail.data.mapper

import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.employees.domain.entity.EmployeeStatus

fun EmployeeDetailSyncEntity.toEmployeeDetailDomain(): EmployeeDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    listInfo.add(ObjectInfoDomain(R.string.employees_lastname, employee.lastname))
    listInfo.add(ObjectInfoDomain(R.string.employees_firstname, employee.firstname))
    listInfo.add(ObjectInfoDomain(R.string.employees_patronymic, employee.patronymic))
    listInfo.add(ObjectInfoDomain(R.string.employees_number, employee.number))
    employee.statusId?.toEmployeeStatus()?.let {
        listInfo.add(ObjectInfoDomain(R.string.employees_status, valueRes = it.titleId))
    }
    structuralSyncEntity?.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.manual_structural, it))
    }
    balanceUnit?.let {
        listInfo.add(ObjectInfoDomain(R.string.balance_unit, it.name))
    }
    employee.post?.let {
        listInfo.add(ObjectInfoDomain(R.string.employees_post, it))
    }

    return EmployeeDetailDomain(id = employee.id, name = employee.fullName, listInfo = listInfo)
}

fun String?.toEmployeeStatus() = EmployeeStatus.values().firstOrNull { it.slug == this }