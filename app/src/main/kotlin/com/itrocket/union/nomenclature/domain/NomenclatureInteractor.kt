package com.itrocket.union.nomenclature.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.nomenclature.domain.dependencies.NomenclatureRepository
import kotlinx.coroutines.withContext

class NomenclatureInteractor(
    private val repository: NomenclatureRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getNomenclatures() = withContext(coreDispatchers.io) {
        repository.getNomenclatures()
    }
}