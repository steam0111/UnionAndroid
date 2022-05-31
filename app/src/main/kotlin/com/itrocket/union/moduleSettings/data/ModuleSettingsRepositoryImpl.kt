package com.itrocket.union.moduleSettings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.itrocket.union.moduleSettings.domain.dependencies.ModuleSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ModuleSettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val keyCodePreferencesKey: Preferences.Key<Int>,
) : ModuleSettingsRepository {
    override suspend fun saveKeyCode(keyCode: Int) {
        dataStore.edit {
            it[keyCodePreferencesKey] = keyCode
        }
    }

    override suspend fun getSavedKeyCode(): Flow<Int?> {
        return dataStore.data.map { it[keyCodePreferencesKey] }
    }

}