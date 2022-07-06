package com.itrocket.union.nomenclatureGroupDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.nomenclatureGroupDetail.domain.dependencies.NomenclatureGroupDetailRepository
import com.itrocket.union.nomenclatureGroupDetail.domain.entity.NomenclatureGroupDetailDomain
import kotlinx.coroutines.withContext

class NomenclatureGroupDetailInteractor(
    private val repository: NomenclatureGroupDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getNomenclatureGroupDetail(id: String): NomenclatureGroupDetailDomain =
        withContext(coreDispatchers.io) {
            repository.getNomenclatureGroupById(id)
        }
}