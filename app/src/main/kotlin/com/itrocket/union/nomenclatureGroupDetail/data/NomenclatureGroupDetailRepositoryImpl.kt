package com.itrocket.union.nomenclatureGroupDetail.data

import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.itrocket.union.nomenclatureGroupDetail.data.mapper.toDomain
import com.itrocket.union.nomenclatureGroupDetail.domain.dependencies.NomenclatureGroupDetailRepository
import com.itrocket.union.nomenclatureGroupDetail.domain.entity.NomenclatureGroupDetailDomain

class NomenclatureGroupDetailRepositoryImpl(
    private val syncApi: NomenclatureGroupSyncApi
) : NomenclatureGroupDetailRepository {
    override suspend fun getNomenclatureGroupById(id: String): NomenclatureGroupDetailDomain {
        return syncApi.getNomenclatureGroupDetail(id).toDomain()
    }
}