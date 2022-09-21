package com.itrocket.union.readerPower.domain

import kotlinx.coroutines.withContext
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.moduleSettings.domain.dependencies.ModuleSettingsRepository
import kotlinx.coroutines.flow.firstOrNull
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ReaderPowerInteractor(
    private val serviceEntryManager: ServiceEntryManager,
    private val coreDispatchers: CoreDispatchers,
    private val moduleSettingsRepository: ModuleSettingsRepository
) {

    suspend fun savePower(power: Int?) {
        withContext(coreDispatchers.io) {
            power?.let {
                val realPower = power * READER_POWER_FACTOR
                val realPowerString = realPower.toString()

                serviceEntryManager.applyReadPower(power = realPowerString)
                serviceEntryManager.applyWritePower(power = realPowerString)
                moduleSettingsRepository.saveReaderPower(readerPower = realPower)
            }
        }
    }

    suspend fun getReaderPower(): Int {
        return withContext(coreDispatchers.io) {
            (moduleSettingsRepository.getReaderPower().firstOrNull() ?: 0) / READER_POWER_FACTOR
        }
    }

    companion object {
        const val READER_POWER_FACTOR = 20
    }
}