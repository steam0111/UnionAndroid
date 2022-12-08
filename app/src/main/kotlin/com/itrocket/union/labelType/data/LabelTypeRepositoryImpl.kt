package com.itrocket.union.labelType.data

import com.example.union_sync_api.data.LabelTypeSyncApi
import com.itrocket.union.labelType.data.mapper.map
import com.itrocket.union.labelType.domain.dependencies.LabelTypeRepository
import com.itrocket.union.labelType.domain.entity.LabelTypeDomain
import com.itrocket.core.base.CoreDispatchers

class LabelTypeRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val labelTypeSyncApi: LabelTypeSyncApi
) : LabelTypeRepository {
    override suspend fun getLabelTypes(
        textQuery: String?,
        offset: Long?,
        limit: Long?
    ): List<LabelTypeDomain> {
        return labelTypeSyncApi.getLabelTypes(textQuery = textQuery, offset = offset, limit = limit)
            .map()
    }

}