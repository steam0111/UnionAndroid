package com.itrocket.union.labelTypeDetail.data.mapper

import com.example.union_sync_api.entity.LabelTypeSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.labelTypeDetail.domain.entity.LabelTypeDetailDomain
import com.itrocket.union.utils.getStringDateFromMillis

fun LabelTypeSyncEntity.toDomain(): LabelTypeDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    name?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_name, it))
    }
    description?.let {
        listInfo.add(ObjectInfoDomain(R.string.label_type_description, it))
    }
    code?.let {
        listInfo.add(ObjectInfoDomain(R.string.equipment_type_code, it))
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

    return LabelTypeDetailDomain(listInfo)
}