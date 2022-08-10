package com.itrocket.union.producerDetail.data.mapper

import com.example.union_sync_api.entity.ProducerSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.producerDetail.domain.entity.ProducerDetailDomain
import com.itrocket.union.utils.getStringDateFromMillis

fun ProducerSyncEntity.toDomain(): ProducerDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    name?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_name, it))
    }
    code?.let {
        listInfo.add(ObjectInfoDomain(R.string.producer_code, it))
    }
    dateInsert?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_date_create, getStringDateFromMillis(it)))
    }
    userInserted?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_user_create, it))
    }
    updateDate?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_date_update, getStringDateFromMillis(it)))
    }
    userUpdated?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_user_update, it))
    }
    return ProducerDetailDomain(listInfo)
}