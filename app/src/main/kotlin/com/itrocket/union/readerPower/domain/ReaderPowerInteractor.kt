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
            (moduleSettingsRepository.getReaderPower().firstOrNull()
                ?: DEFAULT_READER_POWER_VALUE) / READER_POWER_FACTOR
        }
    }

    fun changePower(power: Int?): Int? {
        val newPower = if (power != null) {
            validateNewPower(power = power)
        } else {
            power
        }
        return newPower
    }

    fun validateNewPower(power: Int): Int {
        return when {
            power > MAX_READER_POWER -> {
                MAX_READER_POWER
            }
            power < MIN_READER_POWER -> {
                MIN_READER_POWER
            }
            else -> {
                power
            }
        }
    }

    fun increasePower(power: Int): Int {
        return if (power + 1 <= MAX_READER_POWER) {
            power + 1
        } else {
            MIN_READER_POWER
        }
    }

    fun decreasePower(power: Int): Int {
        return if (power - 1 >= MIN_READER_POWER) {
            power - 1
        } else {
            MAX_READER_POWER
        }
    }

    companion object {
        const val READER_POWER_FACTOR = 10
        const val MIN_READER_POWER = 1
        const val MAX_READER_POWER = 10
        private const val DEFAULT_READER_POWER_VALUE = 100
    }
}