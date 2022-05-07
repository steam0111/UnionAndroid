package com.itrocket.union.reserveDetail.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.reserveDetail.domain.dependencies.ReserveDetailRepository
import com.itrocket.core.base.CoreDispatchers

class ReserveDetailInteractor(
    private val repository: ReserveDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {

}