package com.itrocket.union.branchDetail.data.mapper

import com.example.union_sync_api.entity.BranchDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.branchDetail.domain.entity.BranchDetailDomain

fun BranchDetailSyncEntity.toBranchDetailDomain(): BranchDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()

    branch.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_name, it))
    }
    branch.code?.let {
        listInfo.add(ObjectInfoDomain(R.string.branches_code, it))
    }
    organization?.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.organization, it))
    }
    return BranchDetailDomain(listInfo)
}

