package com.itrocket.union.container.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class IsUserAuthorizedUseCase(
    private val dataStore: DataStore<Preferences>,
    private val accessTokenPreferencesKey: Preferences.Key<String>,
    private val coreDispatchers: CoreDispatchers,
) {
    suspend fun execute(): Boolean = withContext(coreDispatchers.io) {
        val accessToken: String = dataStore.data.map {
            it[accessTokenPreferencesKey].orEmpty()
        }.first()
        accessToken.isNotBlank()
    }
}