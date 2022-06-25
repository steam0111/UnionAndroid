package com.itrocket.union.syncAll.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.union_sync_api.data.AllSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.syncAll.domain.dependencies.SyncAllRepository
import kotlinx.coroutines.withContext

class SyncAllRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: AllSyncApi,
    private val dataStore: DataStore<Preferences>,
    private val isSyncDbPreferenceKey: Preferences.Key<Boolean>
) : SyncAllRepository {

    override suspend fun syncAll(): Unit = withContext(coreDispatchers.ui) {
        syncApi.syncAll()
        dataStore.edit { preferences ->
            preferences[isSyncDbPreferenceKey] = true
        }
    }
}