package com.itrocket.union.labelTypeDetail.data

import com.example.union_sync_api.data.LabelTypeSyncApi
import com.itrocket.union.labelTypeDetail.domain.dependencies.LabelTypeDetailRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.labelType.data.mapper.map
import com.itrocket.union.labelType.domain.entity.LabelTypeDomain
import com.itrocket.union.labelTypeDetail.data.mapper.toDomain
import com.itrocket.union.labelTypeDetail.domain.entity.LabelTypeDetailDomain

class LabelTypeDetailRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val labelTypeSyncApi: LabelTypeSyncApi
) : LabelTypeDetailRepository {

    override suspend fun getLabelTypeById(id: String): LabelTypeDetailDomain {
        return labelTypeSyncApi.getLabelTypeById(id).toDomain()
    }
}