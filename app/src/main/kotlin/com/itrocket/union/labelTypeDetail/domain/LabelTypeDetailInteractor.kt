package com.itrocket.union.labelTypeDetail.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.labelTypeDetail.domain.dependencies.LabelTypeDetailRepository
import com.itrocket.core.base.CoreDispatchers

class LabelTypeDetailInteractor(
    private val repository: LabelTypeDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getLabelTypeById(id: String) = withContext(coreDispatchers.io) {
        repository.getLabelTypeById(id)
    }
}