package com.itrocket.union.moduleSettings.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.moduleSettings.domain.dependencies.ModuleSettingsRepository
import com.itrocket.union.readerPower.domain.ReaderPowerInteractor.Companion.READER_POWER_FACTOR
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
        (repository.getReaderPower().firstOrNull() ?: 0) / READER_POWER_FACTOR
    }

    suspend fun applyChanges(
        defaultService: String,
        keyCode: Int,
        readerPower: Int?
    ) {
        withContext(coreDispatchers.io) {
            serviceEntryManager.applyChanges(defaultService)
            serviceEntryManager.restartService()
            readerPower?.let {
                val realPower = it * READER_POWER_FACTOR
                val realPowerString = realPower.toString()

                serviceEntryManager.applyReadPower(realPowerString)
                serviceEntryManager.applyWritePower(realPowerString)
                repository.saveReaderPower(readerPower = realPower)
            }
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