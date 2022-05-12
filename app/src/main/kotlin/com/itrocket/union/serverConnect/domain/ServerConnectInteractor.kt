package com.itrocket.union.serverConnect.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import com.itrocket.core.base.CoreDispatchers

class ServerConnectInteractor(
    private val repository: ServerConnectRepository,
    private val coreDispatchers: CoreDispatchers
) {

}