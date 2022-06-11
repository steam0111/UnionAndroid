package com.itrocket.union.core

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.network.NetworkModule
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class ServerConnectManager(
    private val serverConnectRepository: ServerConnectRepository,
    coreDispatchers: CoreDispatchers
) {

    private val coroutineScope = CoroutineScope(coreDispatchers.io + SupervisorJob())

    init {
        subscribeServerAddress()
    }

    private fun subscribeServerAddress() {
        coroutineScope.launch {
            serverConnectRepository.getServerAddress().collect {
                unloadKoinModules(NetworkModule.module)
                loadKoinModules(NetworkModule.module)
            }
        }
    }
}