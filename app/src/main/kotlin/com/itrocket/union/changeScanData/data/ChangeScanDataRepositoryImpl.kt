package com.itrocket.union.changeScanData.data

import com.itrocket.union.changeScanData.data.mapper.map
import com.itrocket.union.changeScanData.domain.dependencies.ChangeScanDataRepository
import com.itrocket.union.changeScanData.domain.entity.ChangeScanDataDomain
import com.itrocket.core.base.CoreDispatchers

class ChangeScanDataRepositoryImpl(private val coreDispatchers: CoreDispatchers) :
    ChangeScanDataRepository {

}