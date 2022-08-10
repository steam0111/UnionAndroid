package com.itrocket.union.nomenclatureDetail.data.mapper

import com.example.union_sync_api.entity.NomenclatureDetailSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.nomenclatureDetail.domain.entity.NomenclatureDetailDomain
import com.itrocket.union.R
import com.itrocket.union.utils.getStringDateFromMillis

fun NomenclatureDetailSyncEntity.toDomain(): NomenclatureDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    listInfo.add(ObjectInfoDomain(R.string.common_name, nomenclature.name))
    listInfo.add(ObjectInfoDomain(R.string.nomenclature_code, nomenclature.code))
    nomenclatureGroup?.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.nomenclature_group_name, it))
    }
    nomenclature.dateInsert?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_date_create, getStringDateFromMillis(it)))
    }
    nomenclature.userInserted?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_user_create, it))
    }
    nomenclature.updateDate?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_date_update, getStringDateFromMillis(it)))
    }
    nomenclature.userUpdated?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_user_update, it))
    }
    return NomenclatureDetailDomain(listInfo)
}