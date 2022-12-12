package com.itrocket.union.token

import com.itrocket.union.authMain.AuthMainModule
import com.itrocket.union.container.domain.TokenRepository
import com.itrocket.union.core.CoreModule.UNION_DATA_STORE_QUALIFIER
import com.itrocket.union.token.data.TokenRepositoryImpl
import org.koin.dsl.module

object TokenModule {

    val module = module {
        factory<TokenRepository> {
            TokenRepositoryImpl(
                dataStore = get(UNION_DATA_STORE_QUALIFIER),
                accessTokenPreferencesKey = get(AuthMainModule.ACCESS_TOKEN_PREFERENCE_KEY)
            )
        }
    }
}