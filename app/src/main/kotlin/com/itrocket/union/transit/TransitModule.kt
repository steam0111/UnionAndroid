package com.itrocket.union.transit

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.transit.data.TransitRepositoryImpl
import com.itrocket.union.transit.domain.TransitInteractor
import com.itrocket.union.transit.domain.dependencies.TransitRepository
import com.itrocket.union.transit.presentation.store.TransitStore
import com.itrocket.union.transit.presentation.store.TransitStoreFactory
import com.itrocket.union.transit.presentation.view.TransitComposeFragmentArgs
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.transit.domain.TransitAccountingObjectManager
import com.itrocket.union.transit.domain.TransitRemainsManager

object TransitModule {
    val TRANSIT_VIEW_MODEL_QUALIFIER = named("TRANSIT_VIEW_MODEL")

    val module = module {
        viewModel(TRANSIT_VIEW_MODEL_QUALIFIER) { (args: TransitComposeFragmentArgs) ->
            BaseViewModel(get<TransitStore>() {
                parametersOf(args)
            })
        }

        factory<TransitRepository> {
            TransitRepositoryImpl(get(), get())
        }

        factory {
            TransitInteractor(get(), get())
        }

        factory {
            TransitAccountingObjectManager(get(), get(), get(), get())
        }

        factory {
            TransitRemainsManager(get(), get(), get(), get(), get())
        }

        factory { (args: TransitComposeFragmentArgs) ->
            TransitStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.transitArguments,
                get(),
                get(),
                get(),
                get()
            ).create()
        }
    }
}