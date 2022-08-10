package com.itrocket.union.moduleSettings.domain.dependencies

import kotlinx.coroutines.flow.Flow

interface ModuleSettingsRepository {

    suspend fun saveKeyCode(keyCode: Int)

    suspend fun getSavedKeyCode(): Flow<Int?>

    suspend fun getReaderPower(): Flow<String?>

    suspend fun saveReaderPower(readerPower: String)
}