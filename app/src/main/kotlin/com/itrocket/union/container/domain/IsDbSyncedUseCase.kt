package com.itrocket.union.container.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class IsDbSyncedUseCase(
    private val dataStore: DataStore<Preferences>,
    private val isDbSyncedKey: Preferences.Key<Boolean>,
    private val coreDispatchers: CoreDispatchers,
) {
    suspend fun execute(): Boolean = withContext(coreDispatchers.io) {
        val isDbSynced: Boolean = dataStore.data.map {
            it[isDbSyncedKey] ?: false
        }.first()
        isDbSynced
    }
}