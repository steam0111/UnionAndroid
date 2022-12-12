package com.itrocket.union.uniqueDeviceId

import android.app.backup.BackupManager
import android.content.Context.MODE_PRIVATE
import androidx.datastore.preferences.core.stringPreferencesKey
import com.itrocket.union.uniqueDeviceId.store.UniqueDeviceIdRepository
import com.itrocket.union.uniqueDeviceId.store.UniqueDeviceIdRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

object UniqueDeviceIdModule {

    private val UNIQUE_DEVICE_ID_STORE_QUALIFIER = named("UNIQUE_DEVICE_ID_STORE_QUALIFIER")
    private val UNIQUE_DEVICE_ID_PREFERENCE_KEY = named("UNIQUE_DEVICE_ID_PREFERENCE_KEY")

    val module = module {
        single(UNIQUE_DEVICE_ID_STORE_QUALIFIER) {
            androidContext().getSharedPreferences(UNIQUE_DEVICE_ID_FILE_NAME, MODE_PRIVATE)
        }

        factory<UniqueDeviceIdRepository> {
            UniqueDeviceIdRepositoryImpl(
                dataStore = get(UNIQUE_DEVICE_ID_STORE_QUALIFIER),
                backupManager = get(),
                dispatchers = get()
            )
        }

        single(qualifier = UNIQUE_DEVICE_ID_PREFERENCE_KEY) {
            stringPreferencesKey(UNIQUE_DEVICE_ID_PREFERENCE_KEY.value)
        }

        factory {
            BackupManager(androidContext())
        }
    }

    const val UNIQUE_DEVICE_ID_FILE_NAME = "UNIQUE_DEVICE_ID_FILE_NAME"
}