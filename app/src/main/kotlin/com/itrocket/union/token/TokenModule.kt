package com.itrocket.union.token

import com.itrocket.union.authMain.AuthMainModule
import com.itrocket.union.container.domain.TokenRepository
import com.itrocket.union.token.data.TokenRepositoryImpl
import org.koin.dsl.module

object TokenModule {

    val module = module {
        factory<TokenRepository> {
            TokenRepositoryImpl(
                dataStore = get(),
                accessTokenPreferencesKey = get(AuthMainModule.ACCESS_TOKEN_PREFERENCE_KEY)
            )
        }
    }
}