package com.itrocket.union.employeeDetail.data.mapper

import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.utils.getStringDateFromMillis

fun EmployeeDetailSyncEntity.toEmployeeDetailDomain(): EmployeeDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    listInfo.add(ObjectInfoDomain(R.string.employees_lastname, employee.lastname))
    listInfo.add(ObjectInfoDomain(R.string.employees_firstname, employee.firstname))
    listInfo.add(ObjectInfoDomain(R.string.employees_patronymic, employee.patronymic))
    listInfo.add(ObjectInfoDomain(R.string.employees_number, employee.number))
    employeeStatusSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.employees_status, value = it.name))
    }
    structuralSyncEntities?.let {
        listInfo.add(
            ObjectInfoDomain(
                R.string.manual_structural,
                it.joinToString(", ") { it.name })
        )
    }
    balanceUnitSyncEntities?.let {
        listInfo.add(ObjectInfoDomain(R.string.balance_unit, it.joinToString(", ") { it.name }))
    }
    employee.post?.let {
        listInfo.add(ObjectInfoDomain(R.string.employees_post, it))
    }

    employee.dateInsert?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_date_create, getStringDateFromMillis(it)))
    }
    employee.userInserted?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_user_create, it))
    }
    employee.updateDate?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_date_update, getStringDateFromMillis(it)))
    }
    employee.userUpdated?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_user_update, it))
    }

    return EmployeeDetailDomain(
        id = employee.id,
        listInfo = listInfo,
        nfc = employee.nfc,
        firstName = employee.firstname,
        lastName = employee.lastname,
        patronymic = employee.patronymic
    )
}