package com.itrocket.union.moduleSettings.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.moduleSettings.domain.dependencies.ModuleSettingsRepository
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull

class ModuleSettingsInteractor(
    private val repository: ModuleSettingsRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun saveKeyCode(keyCode: Int) {
        withContext(coreDispatchers.io) {
            repository.saveKeyCode(keyCode)
        }
    }

    suspend fun subscribeKeyCode() = withContext(coreDispatchers.io) {
        repository.getSavedKeyCode().distinctUntilChanged()
    }

    suspend fun getKeyCode() = withContext(coreDispatchers.io) {
        repository.getSavedKeyCode().firstOrNull()
    }

}