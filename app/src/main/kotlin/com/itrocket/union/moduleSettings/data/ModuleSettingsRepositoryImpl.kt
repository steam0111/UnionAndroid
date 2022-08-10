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
    private val readerPowerPreferencesKey: Preferences.Key<String>,
) : ModuleSettingsRepository {
    override suspend fun saveKeyCode(keyCode: Int) {
        dataStore.edit { it[keyCodePreferencesKey] = keyCode }
    }

    override suspend fun getSavedKeyCode(): Flow<Int?> {
        return dataStore.data.map { it[keyCodePreferencesKey] }
    }

    override suspend fun getReaderPower(): Flow<String?> {
        return dataStore.data.map { it[readerPowerPreferencesKey] }
    }

    override suspend fun saveReaderPower(readerPower: String) {
        dataStore.edit { it[readerPowerPreferencesKey] = readerPower }
    }
}