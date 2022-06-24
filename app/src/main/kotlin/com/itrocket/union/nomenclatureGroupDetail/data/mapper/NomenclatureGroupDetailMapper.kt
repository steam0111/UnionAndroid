package com.itrocket.union.nomenclatureGroupDetail.data.mapper

import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.nomenclatureGroupDetail.domain.entity.NomenclatureGroupDetailDomain

fun NomenclatureGroupSyncEntity.toDomain() = NomenclatureGroupDetailDomain(
    listOf(ObjectInfoDomain(R.string.nomenclature_name, name))
)