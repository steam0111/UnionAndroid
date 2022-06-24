package com.itrocket.union.moduleSettings.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.moduleSettings.domain.dependencies.ModuleSettingsRepository
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ModuleSettingsInteractor(
    private val repository: ModuleSettingsRepository,
    private val serviceEntryManager: ServiceEntryManager,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getKeyCode() = withContext(coreDispatchers.io) {
        repository.getSavedKeyCode().firstOrNull()
    }

    suspend fun applyChanges(defaultService: String, keyCode: Int) {
        withContext(coreDispatchers.io) {
            serviceEntryManager.applyChanges(defaultService)
            serviceEntryManager.restartService()
            serviceEntryManager.applyReadPower("100")
            serviceEntryManager.applyWritePower("100") //TODO: Пока нет экрана выбора мощности сделал максимальной
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