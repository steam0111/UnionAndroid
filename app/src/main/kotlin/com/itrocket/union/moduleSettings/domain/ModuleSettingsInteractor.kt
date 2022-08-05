package com.itrocket.union.moduleSettings.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.moduleSettings.domain.dependencies.ModuleSettingsRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ModuleSettingsInteractor(
    private val repository: ModuleSettingsRepository,
    private val serviceEntryManager: ServiceEntryManager,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getKeyCode() = withContext(coreDispatchers.io) {
        repository.getSavedKeyCode().firstOrNull()
    }

    suspend fun applyChanges(
        defaultService: String,
        keyCode: Int,
        readPower: String,
        writePower: String
    ) {
        withContext(coreDispatchers.io) {
            serviceEntryManager.applyChanges(defaultService)
            serviceEntryManager.restartService()
            serviceEntryManager.applyReadPower(readPower)
            serviceEntryManager.applyWritePower(writePower)
            repository.saveKeyCode(keyCode)
        }
    }

    suspend fun changeWaitingKeyCode(isWaiting: Boolean) {
        withContext(coreDispatchers.io) {
            serviceEntryManager.isWaitingCode = isWaiting
        }
    }

    suspend fun checkInstalledService() {
        withContext(coreDispatchers.io) {
            serviceEntryManager.checkInstalledServices()
        }
    }

}