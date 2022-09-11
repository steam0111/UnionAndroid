package com.itrocket.union.nomenclatureGroup.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.nomenclatureGroup.domain.dependencies.NomenclatureGroupRepository

class NomenclatureGroupInteractor(
    private val repository: NomenclatureGroupRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getNomenclatureGroups(
        searchQuery: String = "",
        offset: Long? = null,
        limit: Long? = null
    ) = repository.getNomenclatureGroups(searchQuery, limit = limit, offset = offset)
}