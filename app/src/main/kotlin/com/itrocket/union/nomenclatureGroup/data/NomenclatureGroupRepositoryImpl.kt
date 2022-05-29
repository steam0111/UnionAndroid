package com.itrocket.union.nomenclatureGroup.data

import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.nomenclatureGroup.data.mapper.map
import com.itrocket.union.nomenclatureGroup.domain.dependencies.NomenclatureGroupRepository
import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain
import kotlinx.coroutines.withContext

class NomenclatureGroupRepositoryImpl(
    private val nomenclatureGroupDao: NomenclatureGroupDao,
    private val coreDispatchers: CoreDispatchers
) : NomenclatureGroupRepository {
    override suspend fun getNomenclatureGroups(): List<NomenclatureGroupDomain> = withContext(coreDispatchers.io) {
        nomenclatureGroupDao.getAll().map()
    }
}