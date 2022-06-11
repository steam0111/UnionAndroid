package com.itrocket.union.token.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.itrocket.union.container.domain.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class TokenRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val accessTokenPreferencesKey: Preferences.Key<String>,
) : TokenRepository {

    override fun subscribeOnAccessToken(): Flow<String> {
        return dataStore.data.map {
            it[accessTokenPreferencesKey].orEmpty()
        }.distinctUntilChanged()
    }
}