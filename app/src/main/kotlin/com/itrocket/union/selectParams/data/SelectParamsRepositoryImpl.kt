package com.itrocket.union.selectParams.data

import com.example.union_sync_api.data.AccountingObjectStatusSyncApi
import com.example.union_sync_api.data.CounterpartySyncApi
import com.example.union_sync_api.data.DepartmentSyncApi
import com.example.union_sync_api.data.EmployeeSyncApi
import com.example.union_sync_api.data.EquipmentTypeSyncApi
import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.example.union_sync_api.data.OrganizationSyncApi
import com.example.union_sync_api.data.ProducerSyncApi
import com.example.union_sync_api.data.ReceptionItemCategorySyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.toParam
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SelectParamsRepositoryImpl(
    private val organizationSyncApi: OrganizationSyncApi,
    private val employeeSyncApi: EmployeeSyncApi,
    private val statusesSyncApi: AccountingObjectStatusSyncApi,
    private val departmentSyncApi: DepartmentSyncApi,
    private val equipmentTypeSyncApi: EquipmentTypeSyncApi,
    private val producerSyncApi: ProducerSyncApi,
    private val providerSyncApi: CounterpartySyncApi,
    private val nomenclatureGroupSyncApi: NomenclatureGroupSyncApi,
    private val receptionItemCategorySyncApi: ReceptionItemCategorySyncApi,
    private val coreDispatchers: CoreDispatchers
) : SelectParamsRepository {

    override suspend fun getOrganizationList(textQuery: String?): Flow<List<ParamDomain>> {
        return organizationSyncApi.getOrganizations(textQuery = textQuery).map {
            it.map {
                it.toParam()
            }
        }.flowOn(coreDispatchers.io)
    }

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
            emit(statusesSyncApi.getStatuses().map { it.toParam() })
        }.flowOn(coreDispatchers.io)
    }

    override suspend fun getEquipmentTypes(textQuery: String?): Flow<List<ParamDomain>> {
        return equipmentTypeSyncApi.getEquipmentTypes(textQuery = textQuery).map { list ->
            list.map { it.toParam() }
        }
    }

    override suspend fun getDepartments(textQuery: String?): Flow<List<ParamDomain>> {
        return flow {
            emit(departmentSyncApi.getDepartments(textQuery = textQuery).map { it.toParam() })
        }.flowOn(coreDispatchers.io)
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
}
