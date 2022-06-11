package com.itrocket.union.nomenclatureGroup.data.mapper

import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity
import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain

fun List<NomenclatureGroupSyncEntity>.map(): List<NomenclatureGroupDomain> = map {
    NomenclatureGroupDomain(id = it.id, name = it.name)
}