package com.itrocket.union.filterValues

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.filterValues.presentation.store.FilterValueArguments
import com.itrocket.union.filterValues.presentation.store.FilterValueStore
import com.itrocket.union.filterValues.presentation.store.FilterValueStoreFactory
import com.itrocket.union.filterValues.presentation.view.FilterValueComposeFragment.Companion.FILTER_ARG
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object FilterValueModule {
    val FILTERVALUE_VIEW_MODEL_QUALIFIER = named("FILTERVALUE_VIEW_MODEL")

    val module = module {
        viewModel(FILTERVALUE_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<FilterValueStore> {
                parametersOf(args.getParcelable<FilterValueArguments>(FILTER_ARG))
            })
        }

        factory { (filterArgs: FilterValueArguments) ->
            FilterValueStoreFactory(
                DefaultStoreFactory,
                get(),
                filterArgs,
                get()
            ).create()
        }
    }
}