package com.itrocket.union.container

import com.itrocket.union.authMain.AuthMainModule
import com.itrocket.union.container.domain.IsUserAuthorizedUseCase
import com.itrocket.union.container.domain.OnSessionExpiredUseCase
import com.itrocket.union.container.domain.ScannerManager
import com.itrocket.union.container.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ContainerModule {
    val module = module {
        factory {
            IsUserAuthorizedUseCase(
                get(),
                get(AuthMainModule.ACCESS_TOKEN_PREFERENCE_KEY),
                get()
            )
        }

        factory {
            OnSessionExpiredUseCase(get())
        }

        viewModel {
            MainViewModel(get(), get())
        }

        single {
            ScannerManager(get(), get(), get())
        }
    }
}