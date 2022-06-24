package com.itrocket.union.nomenclature.data.mapper

import com.example.union_sync_api.entity.NomenclatureSyncEntity
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain


fun List<NomenclatureSyncEntity>.map(): List<NomenclatureDomain> = map {
    NomenclatureDomain(it.id, it.catalogItemName + " " + it.code)
}