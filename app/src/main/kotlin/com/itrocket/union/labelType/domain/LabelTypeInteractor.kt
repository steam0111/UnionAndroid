package com.itrocket.union.labelType.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.labelType.domain.dependencies.LabelTypeRepository
import com.itrocket.core.base.CoreDispatchers

class LabelTypeInteractor(
    private val repository: LabelTypeRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getLabelTypes(
        searchQuery: String = "",
        offset: Long? = null,
        limit: Long? = null
    ) = withContext(coreDispatchers.io) {
        repository.getLabelTypes(textQuery = searchQuery, limit = limit, offset = offset)
    }
}