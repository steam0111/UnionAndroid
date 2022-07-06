package com.itrocket.union.producerDetail.data.mapper

import com.example.union_sync_api.entity.ProducerSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.producerDetail.domain.entity.ProducerDetailDomain

fun ProducerSyncEntity.toDomain(): ProducerDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    name?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_name, it))
    }
    code?.let {
        listInfo.add(ObjectInfoDomain(R.string.producer_code, it))
    }
    return ProducerDetailDomain(listInfo)
}