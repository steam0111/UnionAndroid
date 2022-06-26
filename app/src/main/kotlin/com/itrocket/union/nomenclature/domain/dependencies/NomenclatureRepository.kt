package com.itrocket.union.nomenclature.domain.dependencies

import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain

interface NomenclatureRepository {
    suspend fun getNomenclatures(
        textQuery: String?,
        params: List<ParamDomain>?
    ): List<NomenclatureDomain>
}