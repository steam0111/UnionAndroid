package com.itrocket.union.uniqueDeviceId.data

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

            UniqueDeviceId(generatedDeviceId, isGenerateNow = true)
        } else {
            backupManager.dataChanged()
            UniqueDeviceId(deviceIdFromStore, isGenerateNow = false)
        }
    }

    override suspend fun clearDeviceId() {
        dataStore.edit {
            putString(UNIQUE_DEVICE_ID_STRING_KEY, null)
        }
    }

    override suspend fun saveDeviceId(id: String) {
        dataStore.edit {
            putString(UNIQUE_DEVICE_ID_STRING_KEY, id)
        }
        backupManager.dataChanged()
    }

    private companion object {
        private const val UNIQUE_DEVICE_ID_STRING_KEY = "UNIQUE_DEVICE_ID_STRING_KEY"
    }
}