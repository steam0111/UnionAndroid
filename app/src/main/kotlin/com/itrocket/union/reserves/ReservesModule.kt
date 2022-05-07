package com.itrocket.union.reserves

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.reserves.data.ReservesRepositoryImpl
import com.itrocket.union.reserves.domain.ReservesInteractor
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.presentation.store.ReservesStore
import com.itrocket.union.reserves.presentation.store.ReservesStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ReservesModule {
    val RESERVES_VIEW_MODEL_QUALIFIER = named("RESERVES_VIEW_MODEL")

    val module = module {
        viewModel(RESERVES_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<ReservesStore>())
        }

        factory<ReservesRepository> {
            ReservesRepositoryImpl()
        }

        factory {
            ReservesInteractor(get(), get())
        }

        factory {
            ReservesStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}