package com.itrocket.union.labelType.domain.dependencies

import com.itrocket.union.labelType.domain.entity.LabelTypeDomain

interface LabelTypeRepository {

    suspend fun getLabelTypes(
        textQuery: String?,
        offset: Long?,
        limit: Long?
    ): List<LabelTypeDomain>
}