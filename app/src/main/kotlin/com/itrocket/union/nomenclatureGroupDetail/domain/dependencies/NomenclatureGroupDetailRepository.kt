package com.itrocket.union.nomenclatureGroupDetail.domain.dependencies

import com.itrocket.union.nomenclatureGroupDetail.domain.entity.NomenclatureGroupDetailDomain

interface NomenclatureGroupDetailRepository {
    suspend fun getNomenclatureGroupById(id: String): NomenclatureGroupDetailDomain
}