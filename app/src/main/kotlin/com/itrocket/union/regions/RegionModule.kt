package com.itrocket.union.regions

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.regions.data.RegionRepositoryImpl
import com.itrocket.union.regions.domain.RegionInteractor
import com.itrocket.union.regions.domain.dependencies.RegionRepository
import com.itrocket.union.regions.presentation.store.RegionStore
import com.itrocket.union.regions.presentation.store.RegionStoreFactory
import com.itrocket.core.base.BaseViewModel

object RegionModule {
    val REGION_VIEW_MODEL_QUALIFIER = named("REGION_VIEW_MODEL")

    val module = module {
        viewModel(REGION_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<RegionStore>())
        }

        factory<RegionRepository> {
            RegionRepositoryImpl(get(), get())
        }

        factory {
            RegionInteractor(get(), get())
        }

        factory {
            RegionStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get()
            ).create()
        }
    }
}