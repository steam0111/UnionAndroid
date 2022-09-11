package com.itrocket.union.nomenclature.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.nomenclature.domain.dependencies.NomenclatureRepository
import kotlinx.coroutines.withContext

class NomenclatureInteractor(
    private val repository: NomenclatureRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getNomenclatures(
        params: List<ParamDomain>?,
        searchQuery: String = "",
        offset: Long? = null,
        limit: Long? = null
    ) = withContext(coreDispatchers.io) {
        repository.getNomenclatures(searchQuery, params, limit = limit, offset = offset)
    }

    fun getFilters(): List<ParamDomain> {
        return listOf(
            ParamDomain(
                type = ManualType.NOMENCLATURE_GROUP,
                value = ""
            )
        )
    }
}