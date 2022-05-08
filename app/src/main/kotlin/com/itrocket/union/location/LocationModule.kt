package com.itrocket.union.location

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.location.data.LocationRepositoryImpl
import com.itrocket.union.location.domain.LocationInteractor
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.location.presentation.store.LocationStore
import com.itrocket.union.location.presentation.store.LocationStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object LocationModule {
    val LOCATION_VIEW_MODEL_QUALIFIER = named("LOCATION_VIEW_MODEL")

    val module = module {
        viewModel(LOCATION_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<LocationStore>())
        }

        factory<LocationRepository> {
            LocationRepositoryImpl()
        }

        factory {
            LocationInteractor(get(), get())
        }

        factory {
            LocationStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}