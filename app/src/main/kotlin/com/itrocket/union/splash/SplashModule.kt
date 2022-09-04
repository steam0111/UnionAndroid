package com.itrocket.union.splash

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.splash.presentation.store.SplashStore
import com.itrocket.union.splash.presentation.store.SplashStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SplashModule {
    val SPLASH_VIEW_MODEL_QUALIFIER = named("SPLASH_VIEW_MODEL")

    val module = module {
        viewModel(SPLASH_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<SplashStore>())
        }

        factory {
            SplashInteractor(
                isDbSyncedUseCase = get(),
                isUserAuthorizedUseCase = get(),
                coreDispatchers = get()
            )
        }

        factory {
            SplashStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get(),
            ).create()
        }
    }
}