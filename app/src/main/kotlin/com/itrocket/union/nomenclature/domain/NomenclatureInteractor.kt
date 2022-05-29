package com.itrocket.union.nomenclature.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.nomenclature.domain.dependencies.NomenclatureRepository

class NomenclatureInteractor(
    private val repository: NomenclatureRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getNomenclatures() = repository.getNomenclatures()
}