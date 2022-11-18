package com.itrocket.union.moduleSettings.domain.dependencies

import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import kotlinx.coroutines.flow.Flow

interface ModuleSettingsRepository {

    suspend fun saveDefaultReadingMode(readingModeTab: ReadingModeTab)

    suspend fun getDefaultReadingMode(): String?

    suspend fun saveKeyCode(keyCode: Int)

    suspend fun getSavedKeyCode(): Flow<Int?>

    suspend fun getReaderPower(): Flow<Int?>

    suspend fun saveReaderPower(readerPower: Int)

    suspend fun changeDynamicSaveInventory(isDynamicChangeInventory: Boolean)

    suspend fun getDynamicSaveInventory(): Boolean
}