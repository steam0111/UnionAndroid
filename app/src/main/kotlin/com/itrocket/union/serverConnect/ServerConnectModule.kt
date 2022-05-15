package com.itrocket.union.serverConnect

import androidx.datastore.preferences.core.stringPreferencesKey
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.serverConnect.data.ServerConnectRepositoryImpl
import com.itrocket.union.serverConnect.domain.ServerConnectInteractor
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import com.itrocket.union.serverConnect.presentation.store.ServerConnectStore
import com.itrocket.union.serverConnect.presentation.store.ServerConnectStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ServerConnectModule {
    val SERVERCONNECT_VIEW_MODEL_QUALIFIER = named("SERVERCONNECT_VIEW_MODEL")
    val BASE_URL_PREFERENCE_KEY = named("BASE_URL_PREFERENCE_KEY")
    val PORT_PREFERENCE_KEY = named("PORT_PREFERENCE_KEY")

    val module = module {
        viewModel(SERVERCONNECT_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<ServerConnectStore>())
        }

        factory<ServerConnectRepository> {
            ServerConnectRepositoryImpl(
                baseUrlPreferencesKey = get(BASE_URL_PREFERENCE_KEY),
                portPreferencesKey = get(PORT_PREFERENCE_KEY),
                dataStore = get()
            )
        }

        factory {
            ServerConnectInteractor(get(), get())
        }

        factory {
            ServerConnectStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }

        single(qualifier = BASE_URL_PREFERENCE_KEY) {
            stringPreferencesKey(BASE_URL_PREFERENCE_KEY.value)
        }

        single(qualifier = PORT_PREFERENCE_KEY) {
            stringPreferencesKey(PORT_PREFERENCE_KEY.value)
        }
    }
}