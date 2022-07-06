package com.itrocket.union.nomenclature.domain.dependencies

import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain

interface NomenclatureRepository {
    suspend fun getNomenclatures(
        textQuery: String? = null,
        params: List<ParamDomain>?
    ): List<NomenclatureDomain>
}