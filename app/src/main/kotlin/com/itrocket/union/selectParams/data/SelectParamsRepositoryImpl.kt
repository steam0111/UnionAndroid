package com.itrocket.union.selectParams.data

import com.example.union_sync_api.data.CounterpartySyncApi
import com.example.union_sync_api.data.EmployeeSyncApi
import com.example.union_sync_api.data.EnumsSyncApi
import com.example.union_sync_api.data.EquipmentTypeSyncApi
import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.example.union_sync_api.data.ProducerSyncApi
import com.example.union_sync_api.data.ReceptionItemCategorySyncApi
import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.entity.EnumType
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.toParam
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SelectParamsRepositoryImpl(
    private val structuralSyncApi: StructuralSyncApi,
    private val employeeSyncApi: EmployeeSyncApi,
    private val equipmentTypeSyncApi: EquipmentTypeSyncApi,
    private val producerSyncApi: ProducerSyncApi,
    private val providerSyncApi: CounterpartySyncApi,
    private val nomenclatureGroupSyncApi: NomenclatureGroupSyncApi,
    private val receptionItemCategorySyncApi: ReceptionItemCategorySyncApi,
    private val coreDispatchers: CoreDispatchers,
    private val enumsSynApi: EnumsSyncApi
) : SelectParamsRepository {

    override suspend fun getEmployees(
        type: ManualType,
        textQuery: String?
    ): Flow<List<ParamDomain>> {
        return flow {
            emit(employeeSyncApi.getEmployees(textQuery = textQuery).map { it.toParam(type) })
        }.flowOn(coreDispatchers.io)
    }

    override suspend fun getStatuses(textQuery: String?): Flow<List<ParamDomain>> {
        return flow {
            emit(
                enumsSynApi.getAllByType(enumType = EnumType.ACCOUNTING_OBJECT_STATUS)
                    .map { it.toParam(ManualType.STATUS) })
        }.flowOn(coreDispatchers.io)
    }

    override suspend fun getEquipmentTypes(textQuery: String?): Flow<List<ParamDomain>> {
        return equipmentTypeSyncApi.getEquipmentTypes(textQuery = textQuery).map { list ->
            list.map { it.toParam() }
        }
    }

    override suspend fun getProviders(textQuery: String?): Flow<List<ParamDomain>> {
        return providerSyncApi.getCounterparties(textQuery = textQuery).map { list ->
            list.map { it.toParam() }
        }
    }

    override suspend fun getProducers(textQuery: String?): Flow<List<ParamDomain>> {
        return producerSyncApi.getProducers(textQuery = textQuery).map { list ->
            list.map { it.toParam() }
        }
    }

    override suspend fun getNomenclatureGroup(textQuery: String?): Flow<List<ParamDomain>> {
        return flow {
            emit(
                nomenclatureGroupSyncApi.getNomenclatureGroups(textQuery = textQuery)
                    .map { it.toParam() })
        }.flowOn(coreDispatchers.io)
    }

    override suspend fun getReceptionCategory(textQuery: String?): Flow<List<ParamDomain>> {
        return flow {
            emit(receptionItemCategorySyncApi.getAll(textQuery = textQuery).map { it.toParam() })
        }.flowOn(coreDispatchers.io)
    }

    override suspend fun getActionBases(textQuery: String?): Flow<List<ParamDomain>> {
        return flow {
            emit(
                enumsSynApi.getAllByType(enumType = EnumType.ACTION_BASE, textQuery = textQuery)
                    .map { it.toParam(ManualType.ACTION_BASE) })
        }.flowOn(coreDispatchers.io)
    }

    override suspend fun getInventoryBases(textQuery: String?): Flow<List<ParamDomain>> {
        return flow {
            emit(
                enumsSynApi.getAllByType(enumType = EnumType.INVENTORY_BASE, textQuery = textQuery)
                    .map { it.toParam(ManualType.INVENTORY_BASE) })
        }.flowOn(coreDispatchers.io)
    }

    override suspend fun getEmployeesByStructural(
        structuralId: String?,
        type: ManualType,
        textQuery: String?
    ): Flow<List<ParamDomain>> {
        return flow {
            emit(
                employeeSyncApi.getEmployees(textQuery = textQuery, structuralId = structuralId)
                    .map { it.toParam(type) })
        }.flowOn(coreDispatchers.io)
    }
}
