package com.itrocket.union.nomenclature.domain.dependencies

import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain

interface NomenclatureRepository {
    suspend fun getNomenclatures(): List<NomenclatureDomain>
}