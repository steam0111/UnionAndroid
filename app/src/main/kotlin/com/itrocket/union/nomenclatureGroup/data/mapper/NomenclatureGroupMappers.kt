package com.itrocket.union.nomenclatureGroup.data.mapper

import com.example.union_sync_impl.entity.NomenclatureGroup
import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain

fun List<NomenclatureGroup>.map(): List<NomenclatureGroupDomain> = map {
    NomenclatureGroupDomain(id = it.id, name = it.catalogItemName)
}