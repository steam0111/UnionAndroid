package com.itrocket.union.reserves

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.reserves.data.ReservesRepositoryImpl
import com.itrocket.union.reserves.domain.ReservesInteractor
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.presentation.store.ReservesStore
import com.itrocket.union.reserves.presentation.store.ReservesStoreFactory
import com.itrocket.union.reserves.presentation.view.ReservesComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ReservesModule {
    val RESERVES_VIEW_MODEL_QUALIFIER = named("RESERVES_VIEW_MODEL")

    val module = module {
        viewModel(RESERVES_VIEW_MODEL_QUALIFIER) { (args: ReservesComposeFragmentArgs) ->
            BaseViewModel(get<ReservesStore>() {
                parametersOf(args)
            })
        }

        factory<ReservesRepository> {
            ReservesRepositoryImpl(syncApi = get(), coreDispatchers = get())
        }

        factory {
            ReservesInteractor(get(), get(), get(), get())
        }

        factory { (args: ReservesComposeFragmentArgs) ->
            ReservesStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get(),
                args.reservesArguments
            ).create()
        }
    }
}