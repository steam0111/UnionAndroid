package com.itrocket.union.nomenclatureDetail.data

import com.example.union_sync_api.data.NomenclatureSyncApi
import com.itrocket.union.nomenclatureDetail.data.mapper.toDomain
import com.itrocket.union.nomenclatureDetail.domain.dependencies.NomenclatureDetailRepository
import com.itrocket.union.nomenclatureDetail.domain.entity.NomenclatureDetailDomain

class NomenclatureDetailRepositoryImpl(
    private val syncApi: NomenclatureSyncApi
) : NomenclatureDetailRepository {

    override suspend fun getNomenclatureById(id: String): NomenclatureDetailDomain {
        return syncApi.getNomenclatureDetail(id).toDomain()
    }
}
