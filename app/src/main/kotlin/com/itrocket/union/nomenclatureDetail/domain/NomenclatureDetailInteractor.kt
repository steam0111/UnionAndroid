package com.itrocket.union.nomenclatureDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.nomenclatureDetail.domain.dependencies.NomenclatureDetailRepository
import com.itrocket.union.nomenclatureDetail.domain.entity.NomenclatureDetailDomain
import kotlinx.coroutines.withContext

class NomenclatureDetailInteractor(
    private val repository: NomenclatureDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getNomenclatureDetail(id: String): NomenclatureDetailDomain =
        withContext(coreDispatchers.io) {
            repository.getNomenclatureById(id)
        }
}