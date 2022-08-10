package com.itrocket.union.conterpartyDetail.data.mapper

import com.example.union_sync_api.entity.CounterpartySyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.conterpartyDetail.domain.entity.CounterpartyDetailDomain
import com.itrocket.union.utils.getStringDateFromMillis

fun CounterpartySyncEntity.toCounterpartyDetailDomain(): CounterpartyDetailDomain {
    val listInfo = buildList {
        add(ObjectInfoDomain(R.string.common_name, name))
        code?.let {
            add(ObjectInfoDomain(R.string.common_code, it))
        }
        dateInsert?.let {
            add(ObjectInfoDomain(R.string.common_date_create, getStringDateFromMillis(it)))
        }
        userInserted?.let {
            add(ObjectInfoDomain(R.string.common_user_create, it))
        }
        updateDate?.let {
            add(ObjectInfoDomain(R.string.common_date_update, getStringDateFromMillis(it)))
        }
        userUpdated?.let {
            add(ObjectInfoDomain(R.string.common_user_update, it))
        }
    }
    return CounterpartyDetailDomain(listInfo)
}

