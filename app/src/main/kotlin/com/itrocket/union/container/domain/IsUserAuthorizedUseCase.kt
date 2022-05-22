package com.itrocket.union.container.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class IsUserAuthorizedUseCase(
    private val dataStore: DataStore<Preferences>,
    private val accessTokenPreferencesKey: Preferences.Key<String>
) {
    suspend fun execute(): Boolean {
        val accessToken: String = dataStore.data.map {
            it[accessTokenPreferencesKey].orEmpty()
        }.first()

        return accessToken.isNotBlank()
    }
}