package com.itrocket.union.conterpartyDetail.data.mapper

import com.example.union_sync_api.entity.CounterpartySyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.conterpartyDetail.domain.entity.CounterpartyDetailDomain

fun CounterpartySyncEntity.toCounterpartyDetailDomain(): CounterpartyDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    listInfo.add(ObjectInfoDomain(R.string.common_name, name))
    legalAddress?.let {
        listInfo.add(ObjectInfoDomain(R.string.organization_legal_address, it))
    }
    actualAddress?.let {
        listInfo.add(ObjectInfoDomain(R.string.organization_actual_address, it))
    }
    kpp?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_kpp, it))
    }
    inn?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_inn, it))
    }
    return CounterpartyDetailDomain(listInfo)
}

