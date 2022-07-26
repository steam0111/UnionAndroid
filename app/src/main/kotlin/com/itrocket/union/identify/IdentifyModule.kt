package com.itrocket.union.identify

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.identify.data.IdentifyRepositoryImpl
import com.itrocket.union.identify.domain.IdentifyInteractor
import com.itrocket.union.identify.domain.dependencies.IdentifyRepository
import com.itrocket.union.identify.presentation.store.IdentifyStore
import com.itrocket.union.identify.presentation.store.IdentifyStoreFactory
import com.itrocket.union.identify.presentation.view.IdentifyComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object IdentifyModule {
    val IDENTIFY_VIEW_MODEL_QUALIFIER = named("IDENTIFY_VIEW_MODEL")

    val module = module {
        viewModel(IDENTIFY_VIEW_MODEL_QUALIFIER) {(args: IdentifyComposeFragmentArgs) ->
            BaseViewModel(get<IdentifyStore>() {
                parametersOf(args)
            })
        }

        factory<IdentifyRepository> {
            IdentifyRepositoryImpl()
        }

        factory {
            IdentifyInteractor(get(), get(), get())
        }

        factory {(args: IdentifyComposeFragmentArgs) ->
            IdentifyStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.identifyArguments,
                get(),
                get(),
                get()
            ).create()
        }
    }
}