package com.itrocket.union.nomenclatureGroup.domain.dependencies

import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain

interface NomenclatureGroupRepository {
    suspend fun getNomenclatureGroups(): List<NomenclatureGroupDomain>
}