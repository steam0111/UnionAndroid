package com.itrocket.union.authUser

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.authUser.data.AuthUserRepositoryImpl
import com.itrocket.union.authUser.domain.AuthUserInteractor
import com.itrocket.union.authUser.domain.dependencies.AuthUserRepository
import com.itrocket.union.authUser.presentation.store.AuthUserStore
import com.itrocket.union.authUser.presentation.store.AuthUserStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AuthUserModule {
    val AUTHUSER_VIEW_MODEL_QUALIFIER = named("AUTHUSER_VIEW_MODEL")

    val module = module {
        viewModel(AUTHUSER_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<AuthUserStore>())
        }

        factory<AuthUserRepository> {
            AuthUserRepositoryImpl()
        }

        factory {
            AuthUserInteractor(get(), get())
        }

        factory {
            AuthUserStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}