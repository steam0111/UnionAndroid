package com.itrocket.union.departmentDetail.data.mapper

import com.example.union_sync_api.entity.DepartmentDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.departmentDetail.domain.entity.DepartmentDetailDomain

fun DepartmentDetailSyncEntity.toDomain(): DepartmentDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    listInfo.add(ObjectInfoDomain(R.string.department_name, department.name))
    listInfo.add(ObjectInfoDomain(R.string.department_code, department.code))
    organization?.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.organization, it))
    }

    return DepartmentDetailDomain(listInfo)
}