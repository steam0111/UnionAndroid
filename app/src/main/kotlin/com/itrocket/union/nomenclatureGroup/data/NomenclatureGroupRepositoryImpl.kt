package com.itrocket.union.nomenclatureGroup.data

import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.nomenclatureGroup.data.mapper.map
import com.itrocket.union.nomenclatureGroup.domain.dependencies.NomenclatureGroupRepository
import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain
import kotlinx.coroutines.withContext

class NomenclatureGroupRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val nomenclatureGroupSyncApi: NomenclatureGroupSyncApi
) : NomenclatureGroupRepository {
    override suspend fun getNomenclatureGroups(textQuery: String?): List<NomenclatureGroupDomain> = withContext(coreDispatchers.io) {
        nomenclatureGroupSyncApi.getNomenclatureGroups(textQuery).map()
    }
}