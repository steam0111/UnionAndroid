package com.itrocket.union.nomenclature.data

import com.example.union_sync_impl.dao.NomenclatureDao
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.nomenclature.data.mapper.map
import com.itrocket.union.nomenclature.domain.dependencies.NomenclatureRepository
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain
import kotlinx.coroutines.withContext

class NomenclatureRepositoryImpl(
    private val nomenclatureDao: NomenclatureDao,
    private val coreDispatchers: CoreDispatchers
) : NomenclatureRepository {

    override suspend fun getNomenclatures(): List<NomenclatureDomain> = withContext(coreDispatchers.io) {
        nomenclatureDao.getAll().map()
    }
}