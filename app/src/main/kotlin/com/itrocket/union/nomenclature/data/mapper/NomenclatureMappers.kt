package com.itrocket.union.nomenclature.data.mapper

import com.example.union_sync_impl.entity.NomenclatureDb
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain

fun List<NomenclatureDb>.map(): List<NomenclatureDomain> = map {
    NomenclatureDomain(it.id, it.catalogItemName + " " + it.number)
}