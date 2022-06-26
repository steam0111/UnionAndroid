package com.itrocket.union.authMain

import androidx.datastore.preferences.core.stringPreferencesKey
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.authMain.data.AuthMainRepositoryImpl
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.presentation.store.AuthMainStore
import com.itrocket.union.authMain.presentation.store.AuthMainStoreFactory
import com.itrocket.union.authMain.presentation.view.AuthMainComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AuthMainModule {
    val AUTHMAIN_VIEW_MODEL_QUALIFIER = named("AUTHMAIN_VIEW_MODEL")
    val ACCESS_TOKEN_PREFERENCE_KEY = named("ACCESS_TOKEN_PREFERENCE_KEY")
    val REFRESH_TOKEN_PREFERENCE_KEY = named("REFRESH_TOKEN_PREFERENCE_KEY")
    val LOGIN_PREFERENCE_KEY = named("LOGIN_PREFERENCE_KEY")

    val module = module {
        viewModel(AUTHMAIN_VIEW_MODEL_QUALIFIER) { (args: AuthMainComposeFragmentArgs) ->
            BaseViewModel(get<AuthMainStore>() {
                parametersOf(args)
            })
        }

        factory<AuthMainRepository> {
            AuthMainRepositoryImpl(
                api = get(),
                dataStore = get(),
                accessTokenPreferencesKey = get(ACCESS_TOKEN_PREFERENCE_KEY),
                refreshTokenPreferencesKey = get(REFRESH_TOKEN_PREFERENCE_KEY),
                loginPreferencesKey = get(LOGIN_PREFERENCE_KEY)
            )
        }

        factory {
            AuthMainInteractor(get(), get())
        }

        factory { (args: AuthMainComposeFragmentArgs) ->
            AuthMainStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.authMainComposeFragmentArgs,
                get()
            ).create()
        }

        single(qualifier = ACCESS_TOKEN_PREFERENCE_KEY) {
            stringPreferencesKey(ACCESS_TOKEN_PREFERENCE_KEY.value)
        }

        single(qualifier = REFRESH_TOKEN_PREFERENCE_KEY) {
            stringPreferencesKey(REFRESH_TOKEN_PREFERENCE_KEY.value)
        }

        single(qualifier = LOGIN_PREFERENCE_KEY) {
            stringPreferencesKey(LOGIN_PREFERENCE_KEY.value)
        }
    }
}