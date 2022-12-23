package com.itrocket.union.moduleSettings.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.moduleSettings.domain.dependencies.ModuleSettingsRepository
import com.itrocket.union.readerPower.domain.ReaderPowerInteractor.Companion.READER_POWER_FACTOR
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.readingMode.presentation.view.toReadingMode
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ModuleSettingsInteractor(
    private val repository: ModuleSettingsRepository,
    private val serviceEntryManager: ServiceEntryManager,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getDefaultReadingMode(isForceUpdate: Boolean) = withContext(coreDispatchers.io) {
        val readingMode =
            ReadingModeTab.valueOf(repository.getDefaultReadingMode() ?: ReadingModeTab.RFID.name)
        if (isForceUpdate) {
            saveDefaultReadingMode(readingMode)
        }
        readingMode
    }

    suspend fun saveDefaultReadingMode(readingModeTab: ReadingModeTab) =
        withContext(coreDispatchers.io) {
            repository.saveDefaultReadingMode(readingModeTab)
            serviceEntryManager.changeScanMode(readingModeTab.toReadingMode())
        }

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

    fun changeWaitingKeyCode(isWaiting: Boolean) {
        serviceEntryManager.isWaitingCode = isWaiting
    }

    suspend fun checkInstalledService() {
        withContext(coreDispatchers.io) {
            serviceEntryManager.checkInstalledServices()
        }
    }

    suspend fun changeDynamicSaveInventory(isDynamicChangeInventory: Boolean) {
        withContext(coreDispatchers.io) {
            repository.changeDynamicSaveInventory(isDynamicChangeInventory)
        }
    }

    suspend fun getDynamicSaveInventory(): Boolean {
        return withContext(coreDispatchers.io) {
            repository.getDynamicSaveInventory()
        }
    }

    suspend fun changeSyncFilesEnabled(enabled: Boolean) = withContext(coreDispatchers.io) {
        repository.changeSyncFilesEnabled(enabled)
    }

    suspend fun getSyncFileEnabled() = withContext(coreDispatchers.io) {
        repository.getSyncFileEnabled()
    }
}