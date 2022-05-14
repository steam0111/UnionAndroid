package com.itrocket.union.authSelectUser

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.authSelectUser.data.AuthSelectUserRepositoryImpl
import com.itrocket.union.authSelectUser.domain.AuthSelectUserInteractor
import com.itrocket.union.authSelectUser.domain.dependencies.AuthSelectUserRepository
import com.itrocket.union.authSelectUser.presentation.store.AuthSelectUserStore
import com.itrocket.union.authSelectUser.presentation.store.AuthSelectUserStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AuthSelectUserModule {
    val AUTHSELECTUSER_VIEW_MODEL_QUALIFIER = named("AUTHSELECTUSER_VIEW_MODEL")

    val module = module {
        viewModel(AUTHSELECTUSER_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<AuthSelectUserStore>())
        }

        factory<AuthSelectUserRepository> {
            AuthSelectUserRepositoryImpl()
        }

        factory {
            AuthSelectUserInteractor(get(), get())
        }

        factory {
            AuthSelectUserStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}