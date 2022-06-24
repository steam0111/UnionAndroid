package com.itrocket.union.nomenclatureDetail.data.mapper

import com.example.union_sync_api.entity.NomenclatureDetailSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.nomenclatureDetail.domain.entity.NomenclatureDetailDomain
import com.itrocket.union.R

fun NomenclatureDetailSyncEntity.toDomain(): NomenclatureDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    listInfo.add(ObjectInfoDomain(R.string.common_name, nomenclature.name))
    listInfo.add(ObjectInfoDomain(R.string.nomenclature_code, nomenclature.code))
    nomenclatureGroup?.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.nomenclature_group_name, it))
    }
    return NomenclatureDetailDomain(listInfo)
}