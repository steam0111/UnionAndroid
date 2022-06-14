package com.itrocket.union.selectParams.domain.dependencies

import com.example.union_sync_api.data.OrganizationSyncApi
import com.itrocket.union.manual.ManualType

interface SelectParamsRepository {
    suspend fun getParamValues(type: ManualType, searchText: String): List<String>

    suspend fun getOrganizationList(): List<String>
}