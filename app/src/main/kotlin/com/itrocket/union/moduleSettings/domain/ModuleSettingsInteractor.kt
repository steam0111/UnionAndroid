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

    suspend fun getReaderPower() = withContext(coreDispatchers.io) {
        repository.getReaderPower().firstOrNull()
    }

    suspend fun applyChanges(
        defaultService: String,
        keyCode: Int,
        readerPower: String
    ) {
        withContext(coreDispatchers.io) {
            serviceEntryManager.applyChanges(defaultService)
            serviceEntryManager.restartService()
            serviceEntryManager.applyReadPower(readerPower)
            serviceEntryManager.applyWritePower(readerPower)
            repository.saveKeyCode(keyCode)
            repository.saveReaderPower(readerPower = readerPower)
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
    companion object {
        val MIN_READER_POWER = 0
        val MAX_READER_POWER = 10
    }
}