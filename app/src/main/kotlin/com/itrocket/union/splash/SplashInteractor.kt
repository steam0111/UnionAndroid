package com.itrocket.union.splash

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.container.domain.IsDbSyncedUseCase
import com.itrocket.union.container.domain.IsUserAuthorizedUseCase
import com.itrocket.union.container.presentation.InitialScreen
import kotlinx.coroutines.withContext

class SplashInteractor(
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
    private val isDbSyncedUseCase: IsDbSyncedUseCase,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getInitialScreen(): InitialScreen {
        return withContext(coreDispatchers.io) {
            val isUserAuthorized = isUserAuthorizedUseCase.execute()

            val initialScreen: InitialScreen = when {
                isUserAuthorized -> {
                    val isDbSynced = isDbSyncedUseCase.execute()

                    if (isDbSynced) {
                        InitialScreen.DOCUMENTS_MENU
                    } else {
                        InitialScreen.SYNC_ALL
                    }
                }
                else -> {
                    InitialScreen.AUTH
                }
            }
            initialScreen
        }
    }
}