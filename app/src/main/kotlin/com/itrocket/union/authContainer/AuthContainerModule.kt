package com.itrocket.union.authContainer

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.authContainer.data.AuthContainerRepositoryImpl
import com.itrocket.union.authContainer.domain.AuthContainerInteractor
import com.itrocket.union.authContainer.domain.dependencies.AuthContainerRepository
import com.itrocket.union.authContainer.presentation.store.AuthContainerStore
import com.itrocket.union.authContainer.presentation.store.AuthContainerStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AuthContainerModule {
    val AUTH_VIEW_MODEL_QUALIFIER = named("AUTH_VIEW_MODEL")

    val module = module {
        viewModel(AUTH_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<AuthContainerStore>())
        }

        factory<AuthContainerRepository> {
            AuthContainerRepositoryImpl()
        }

        factory {
            AuthContainerInteractor(get(), get())
        }

        factory {
            AuthContainerStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}