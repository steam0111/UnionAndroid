package com.itrocket.union.uniqueDeviceId.store

import android.app.backup.BackupManager
import android.content.SharedPreferences
import androidx.core.content.edit
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class UniqueDeviceIdRepositoryImpl(
    private val dispatchers: CoreDispatchers,
    private val dataStore: SharedPreferences,
    private val backupManager: BackupManager
) : UniqueDeviceIdRepository {

    override suspend fun getUniqueDeviceId(): UniqueDeviceId = withContext(dispatchers.io) {
        val deviceIdFromStore: String? = dataStore.getString(UNIQUE_DEVICE_ID_STRING_KEY, null)

        if (deviceIdFromStore.isNullOrBlank()) {
            val generatedDeviceId = UUID.randomUUID().toString()

            dataStore.edit {
                putString(UNIQUE_DEVICE_ID_STRING_KEY, generatedDeviceId)
            }
            backupManager.dataChanged()

            UniqueDeviceId(generatedDeviceId, isGenerateNow = true)
        } else {
            UniqueDeviceId(deviceIdFromStore, isGenerateNow = false)
        }
    }

    private companion object {
        private const val UNIQUE_DEVICE_ID_STRING_KEY = "UNIQUE_DEVICE_ID_STRING_KEY"
    }
}