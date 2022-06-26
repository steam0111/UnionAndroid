package com.itrocket.union.selectParams.data

import com.example.union_sync_api.data.AccountingObjectStatusSyncApi
import com.example.union_sync_api.data.CounterpartySyncApi
import com.example.union_sync_api.data.DepartmentSyncApi
import com.example.union_sync_api.data.EmployeeSyncApi
import com.example.union_sync_api.data.EquipmentTypeSyncApi
import com.example.union_sync_api.data.OrganizationSyncApi
import com.example.union_sync_api.data.ProducerSyncApi
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
    private val coreDispatchers: CoreDispatchers
) : SelectParamsRepository {

    fun getMockList(type: ManualType) = listOf(
        ParamDomain("1", "1", type),
        ParamDomain("12", "12", type),
        ParamDomain("2", "2", type),
        ParamDomain("3", "3", type),
        ParamDomain("123", "123", type),
        ParamDomain("122", "122", type),
        ParamDomain("231", "231", type),
        ParamDomain("4444", "4444", type),
        ParamDomain("1111d", "1111d", type),
        ParamDomain("4343", "4343", type),
        ParamDomain("1233212", "123321", type),
        ParamDomain("1233213", "123321", type),
        ParamDomain("1233214", "123321", type),
        ParamDomain("1233215", "123321", type),
        ParamDomain("1233216", "123321", type),
        ParamDomain("1233217", "123321", type),
        ParamDomain("1233218", "123321", type),
        ParamDomain("1233219", "123321", type),
        ParamDomain("1233210", "123321", type),
        ParamDomain("12332100", "123321", type),
        ParamDomain("123321000", "123321", type),
    )

    override suspend fun getParamValues(
        type: ManualType,
        searchText: String
    ): Flow<List<ParamDomain>> {
        return flow {
            emit(getMockList(type).filter {
                it.value.contains(other = searchText, ignoreCase = true)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getOrganizationList(): Flow<List<ParamDomain>> {
        return organizationSyncApi.getOrganizations().map {
            it.map {
                it.toParam()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getEmployees(type: ManualType): Flow<List<ParamDomain>> {
        return flow {
            emit(employeeSyncApi.getEmployees().map { it.toParam(type) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getStatuses(): Flow<List<ParamDomain>> {
        return flow {
            emit(statusesSyncApi.getStatuses().map { it.toParam() })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getEquipmentTypes(): Flow<List<ParamDomain>> {
        return equipmentTypeSyncApi.getEquipmentTypes().map { list ->
            list.map { it.toParam() }
        }
    }

    override suspend fun getDepartments(): Flow<List<ParamDomain>> {
        return flow {
            emit(departmentSyncApi.getDepartments().map { it.toParam() })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getProviders(): Flow<List<ParamDomain>> {
        return providerSyncApi.getCounterparties().map { list ->
            list.map { it.toParam() }
        }
    }

    override suspend fun getProducers(): Flow<List<ParamDomain>> {
        return producerSyncApi.getProducers().map { list ->
            list.map { it.toParam() }
        }
    }
}
