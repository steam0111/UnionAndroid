package com.itrocket.union.selectParams.data

import com.example.union_sync_api.data.OrganizationSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamValueDomain
import com.itrocket.union.manual.toParamValue
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SelectParamsRepositoryImpl(
    private val organizationSyncApi: OrganizationSyncApi,
    private val coreDispatchers: CoreDispatchers
) : SelectParamsRepository {

    val paramMockList = listOf(
        ParamValueDomain("1", "1"),
        ParamValueDomain("12", "12"),
        ParamValueDomain("2", "2"),
        ParamValueDomain("3", "3"),
        ParamValueDomain("123", "123"),
        ParamValueDomain("122", "122"),
        ParamValueDomain("231", "231"),
        ParamValueDomain("4444", "4444"),
        ParamValueDomain("1111d", "1111d"),
        ParamValueDomain("4343", "4343"),
        ParamValueDomain("1233212", "123321"),
        ParamValueDomain("1233213", "123321"),
        ParamValueDomain("1233214", "123321"),
        ParamValueDomain("1233215", "123321"),
        ParamValueDomain("1233216", "123321"),
        ParamValueDomain("1233217", "123321"),
        ParamValueDomain("1233218", "123321"),
        ParamValueDomain("1233219", "123321"),
        ParamValueDomain("1233210", "123321"),
        ParamValueDomain("12332100", "123321"),
        ParamValueDomain("123321000", "123321"),
    )

    override suspend fun getParamValues(
        type: ManualType,
        searchText: String
    ): Flow<List<ParamValueDomain>> {
        return flow {
            emit(paramMockList.filter {
                it.value.contains(other = searchText, ignoreCase = true)
            })
        }
    }

    override suspend fun getOrganizationList(): Flow<List<ParamValueDomain>> {
        return organizationSyncApi.getOrganizations().map {
            it.map {
                it.toParamValue()
            }
        }
    }
}