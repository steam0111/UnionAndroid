package com.itrocket.union.core

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.network.NetworkInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthCredentialsManager(
    private val authMainInteractor: AuthMainInteractor,
    private val networkInfo: NetworkInfo,
    coreDispatchers: CoreDispatchers,
) {
    private val coroutineScope = CoroutineScope(coreDispatchers.io + SupervisorJob())

    init {
        subscribeAuthCredentials()
    }

    private fun subscribeAuthCredentials() {
        coroutineScope.launch {
            authMainInteractor.subscribeAccessToken().collect {
                networkInfo.accessToken = it
            }
        }
        coroutineScope.launch {
            authMainInteractor.subscribeRefreshToken().collect {
                networkInfo.refreshToken = it
            }
        }
    }
}