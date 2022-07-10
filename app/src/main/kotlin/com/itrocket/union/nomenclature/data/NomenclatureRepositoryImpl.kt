package com.itrocket.union.nomenclature.data


import com.example.union_sync_api.data.NomenclatureSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getNomenclatureGroupId
import com.itrocket.union.nomenclature.domain.dependencies.NomenclatureRepository
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain
import kotlinx.coroutines.withContext
import com.itrocket.union.nomenclature.data.mapper.map

class NomenclatureRepositoryImpl(
    private val syncApi: NomenclatureSyncApi,
    private val coreDispatchers: CoreDispatchers
) : NomenclatureRepository {

    override suspend fun getNomenclatures(
        textQuery: String?,
        params: List<ParamDomain>?,
    ): List<NomenclatureDomain> =
        withContext(coreDispatchers.io) {
            syncApi.getNomenclatures(
                groupId = params?.getNomenclatureGroupId(),
                textQuery = textQuery
            ).map()
        }

    override suspend fun getNomenclaturesCount(
        textQuery: String?,
        params: List<ParamDomain>?
    ): Long =
        withContext(coreDispatchers.io) {
            syncApi.getNomenclaturesCount(
                groupId = params?.getNomenclatureGroupId(),
                textQuery = textQuery
            )
        }
}