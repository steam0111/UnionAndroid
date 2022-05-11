package com.itrocket.union.auth

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.auth.data.AuthRepositoryImpl
import com.itrocket.union.auth.domain.AuthInteractor
import com.itrocket.union.auth.domain.dependencies.AuthRepository
import com.itrocket.union.auth.presentation.store.AuthStore
import com.itrocket.union.auth.presentation.store.AuthStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AuthModule {
    val AUTH_VIEW_MODEL_QUALIFIER = named("AUTH_VIEW_MODEL")

    val module = module {
        viewModel(AUTH_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<AuthStore>())
        }

        factory<AuthRepository> {
            AuthRepositoryImpl()
        }

        factory {
            AuthInteractor(get(), get())
        }

        factory {
            AuthStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}