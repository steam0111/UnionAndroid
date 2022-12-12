package com.itrocket.union.container

import com.itrocket.union.authMain.AuthMainModule
import com.itrocket.union.container.domain.IsDbSyncedUseCase
import com.itrocket.union.container.domain.IsUserAuthorizedUseCase
import com.itrocket.union.container.domain.OnSessionExpiredUseCase
import com.itrocket.union.container.domain.ScannerManager
import com.itrocket.union.container.presentation.MainViewModel
import com.itrocket.union.core.CoreModule.UNION_DATA_STORE_QUALIFIER
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ContainerModule {
    val module = module {
        factory {
            IsUserAuthorizedUseCase(
                dataStore = get(UNION_DATA_STORE_QUALIFIER),
                get(AuthMainModule.ACCESS_TOKEN_PREFERENCE_KEY),
                get()
            )
        }
        factory {
            OnSessionExpiredUseCase(get())
        }
        viewModel {
            MainViewModel(get())
        }
        single(createdAtStart = true) {
            ScannerManager(get(), get(), get())
        }
        factory {
            IsDbSyncedUseCase(
                get(),
                get()
            )
        }
    }
}