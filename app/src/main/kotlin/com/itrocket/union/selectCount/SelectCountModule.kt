package com.itrocket.union.selectCount

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.selectCount.presentation.store.SelectCountArguments
import com.itrocket.union.selectCount.presentation.store.SelectCountStore
import com.itrocket.union.selectCount.presentation.store.SelectCountStoreFactory
import com.itrocket.union.selectCount.presentation.view.SelectCountComposeFragment.Companion.SELECT_COUNT_ARG
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SelectCountModule {
    val SELECTCOUNT_VIEW_MODEL_QUALIFIER = named("SELECTCOUNT_VIEW_MODEL")

    val module = module {
        viewModel(SELECTCOUNT_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<SelectCountStore>() {
                parametersOf(args.getParcelable(SELECT_COUNT_ARG))
            })
        }

        factory { (args: SelectCountArguments) ->
            SelectCountStoreFactory(
                DefaultStoreFactory,
                get(),
                args,
                get()
            ).create()
        }
    }
}