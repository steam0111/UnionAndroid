package com.itrocket.union.organizationDetail.data.mapper

import com.example.union_sync_api.entity.OrganizationDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.organizationDetail.domain.entity.OrganizationDetailDomain

fun OrganizationDetailSyncEntity.toOrganizationDetailDomain(): OrganizationDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    listInfo.add(ObjectInfoDomain(R.string.common_name, organization.name))
    organization.legalAddress?.let {
        listInfo.add(ObjectInfoDomain(R.string.organization_legal_address, it))
    }
    organization.actualAddress?.let {
        listInfo.add(ObjectInfoDomain(R.string.organization_actual_address, it))
    }
    organization.kpp?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_kpp, it))
    }
    organization.inn?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_inn, it))
    }
    organization.comment?.let {
        listInfo.add(ObjectInfoDomain(R.string.organization_comment, it))
    }
    employee?.fullName?.let {
        listInfo.add(ObjectInfoDomain(R.string.employees_name, it))
    }
    return OrganizationDetailDomain(listInfo = listInfo, name = organization.name, id = organization.id)
}

