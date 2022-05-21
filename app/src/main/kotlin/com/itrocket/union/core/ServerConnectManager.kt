package com.itrocket.union.core

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.network.NetworkInfo
import com.itrocket.union.network.NetworkModule
import com.itrocket.union.serverConnect.domain.ServerConnectInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class ServerConnectManager(
    private val serverConnectInteractor: ServerConnectInteractor,
    private val networkInfo: NetworkInfo,
    coreDispatchers: CoreDispatchers,
) {

    private val coroutineScope = CoroutineScope(coreDispatchers.io + SupervisorJob())

    init {
        subscribeServerAddress()
    }

    fun subscribeServerAddress() {
        coroutineScope.launch {
            serverConnectInteractor.getServerAddress().collect {
                networkInfo.serverAddress = it
                unloadKoinModules(NetworkModule.module)
                loadKoinModules(NetworkModule.module)
            }
        }
    }
}