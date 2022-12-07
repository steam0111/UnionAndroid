package com.itrocket.union.nomenclature.data.mapper

import com.example.union_sync_api.entity.NomenclatureSyncEntity
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain


fun List<NomenclatureSyncEntity>.map(): List<NomenclatureDomain> = map {
    NomenclatureDomain(id = it.id, it.name, code = it.code, barcode = it.barcode)
}