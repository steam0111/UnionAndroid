package com.itrocket.union.moduleSettings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.itrocket.union.moduleSettings.domain.dependencies.ModuleSettingsRepository
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class ModuleSettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val dynamicSaveInventoryPreferencesKey: Preferences.Key<Boolean>,
    private val keyCodePreferencesKey: Preferences.Key<Int>,
    private val readerPowerPreferencesKey: Preferences.Key<Int>,
    private val readingModePreferencesKey: Preferences.Key<String>
) : ModuleSettingsRepository {

    override suspend fun saveDefaultReadingMode(readingModeTab: ReadingModeTab) {
        dataStore.edit { it[readingModePreferencesKey] = readingModeTab.name }
    }

    override suspend fun getDefaultReadingMode(): String? {
        return dataStore.data.map { it[readingModePreferencesKey] }.firstOrNull()
    }

    override suspend fun saveKeyCode(keyCode: Int) {
        dataStore.edit { it[keyCodePreferencesKey] = keyCode }
    }

    override suspend fun getSavedKeyCode(): Flow<Int?> {
        return dataStore.data.map { it[keyCodePreferencesKey] }
    }

    override suspend fun getReaderPower(): Flow<Int?> {
        return dataStore.data.map { it[readerPowerPreferencesKey] }
    }

    override suspend fun saveReaderPower(readerPower: Int) {
        dataStore.edit { it[readerPowerPreferencesKey] = readerPower }
    }

    override suspend fun changeDynamicSaveInventory(isDynamicChangeInventory: Boolean) {
        dataStore.edit { it[dynamicSaveInventoryPreferencesKey] = isDynamicChangeInventory }
    }

    override suspend fun getDynamicSaveInventory(): Boolean {
        return dataStore.data.map { it[dynamicSaveInventoryPreferencesKey] }.firstOrNull() ?: false
    }
}