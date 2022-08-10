package com.itrocket.union.conterpartyDetail.data.mapper

import com.example.union_sync_api.entity.CounterpartySyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.conterpartyDetail.domain.entity.CounterpartyDetailDomain

fun CounterpartySyncEntity.toCounterpartyDetailDomain(): CounterpartyDetailDomain {
    val listInfo = buildList {
        add(ObjectInfoDomain(R.string.common_name, name))
        code?.let {
            add(ObjectInfoDomain(R.string.common_code, it))
        }
    }
    return CounterpartyDetailDomain(listInfo)
}

