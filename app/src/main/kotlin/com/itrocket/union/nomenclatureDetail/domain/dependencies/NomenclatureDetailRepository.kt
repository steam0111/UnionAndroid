package com.itrocket.union.nomenclatureDetail.domain.dependencies

import com.itrocket.union.nomenclatureDetail.domain.entity.NomenclatureDetailDomain

interface NomenclatureDetailRepository {
    suspend fun getNomenclatureById(id: String): NomenclatureDetailDomain
}