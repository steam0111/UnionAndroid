package com.itrocket.union.filter

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.filter.data.FilterRepositoryImpl
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.filter.domain.dependencies.FilterRepository
import com.itrocket.union.filter.presentation.store.FilterStore
import com.itrocket.union.filter.presentation.store.FilterStoreFactory
import com.itrocket.union.filter.presentation.view.FilterComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object FilterModule {
    val FILTER_VIEW_MODEL_QUALIFIER = named("FILTER_VIEW_MODEL")

    val module = module {
        viewModel(FILTER_VIEW_MODEL_QUALIFIER) { (args: FilterComposeFragmentArgs) ->
            BaseViewModel(get<FilterStore> {
                parametersOf(args)
            })
        }

        factory<FilterRepository> {
            FilterRepositoryImpl()
        }

        factory {
            FilterInteractor(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }

        factory { (args: FilterComposeFragmentArgs) ->
            FilterStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.filterArguments,
                get()
            ).create()
        }
    }
}