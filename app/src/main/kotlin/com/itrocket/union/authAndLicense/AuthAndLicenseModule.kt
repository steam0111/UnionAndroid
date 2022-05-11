package com.itrocket.union.authAndLicense

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.itrocket.union.authAndLicense.data.AuthAndLicenseRepositoryImpl
import com.itrocket.union.authAndLicense.domain.AuthAndLicenseInteractor
import com.itrocket.union.authAndLicense.domain.dependencies.AuthAndLicenseRepository
import com.itrocket.union.authAndLicense.presentation.store.AuthAndLicenseStore
import com.itrocket.union.authAndLicense.presentation.store.AuthAndLicenseStoreFactory
import com.itrocket.core.base.BaseViewModel

object AuthAndLicenseModule {
    val AUTHANDLICENSE_VIEW_MODEL_QUALIFIER = named("AUTHANDLICENSE_VIEW_MODEL")

    val module = module {
        viewModel(AUTHANDLICENSE_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<AuthAndLicenseStore>())
        }

        factory<AuthAndLicenseRepository> {
            AuthAndLicenseRepositoryImpl()
        }

        factory {
            AuthAndLicenseInteractor(get(), get())
        }

        factory {
            AuthAndLicenseStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}