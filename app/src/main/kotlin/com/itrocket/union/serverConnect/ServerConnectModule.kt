package com.itrocket.union.serverConnect

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.itrocket.union.serverConnect.data.ServerConnectRepositoryImpl
import com.itrocket.union.serverConnect.domain.ServerConnectInteractor
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import com.itrocket.union.serverConnect.presentation.store.ServerConnectStore
import com.itrocket.union.serverConnect.presentation.store.ServerConnectStoreFactory
import com.itrocket.core.base.BaseViewModel

object ServerConnectModule {
    val SERVERCONNECT_VIEW_MODEL_QUALIFIER = named("SERVERCONNECT_VIEW_MODEL")

    val module = module {
        viewModel(SERVERCONNECT_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<ServerConnectStore>())
        }

        factory<ServerConnectRepository> {
            ServerConnectRepositoryImpl()
        }

        factory {
            ServerConnectInteractor(get(), get())
        }

        factory {
            ServerConnectStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get()
            ).create()
        }
    }
}