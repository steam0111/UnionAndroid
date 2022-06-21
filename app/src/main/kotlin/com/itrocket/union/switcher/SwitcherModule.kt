package com.itrocket.union.switcher

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.switcher.domain.SwitcherInteractor
import com.itrocket.union.switcher.presentation.store.SwitcherArguments
import com.itrocket.union.switcher.presentation.store.SwitcherStore
import com.itrocket.union.switcher.presentation.store.SwitcherStoreFactory
import com.itrocket.union.switcher.presentation.view.SwitcherComposeFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SwitcherModule {
    val SWITCHER_VIEW_MODEL_QUALIFIER = named("SWITCHER_VIEW_MODEL")

    val module = module {
        viewModel(SWITCHER_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<SwitcherStore>() {
                parametersOf(args.getParcelable<SwitcherArguments>(SwitcherComposeFragment.SWITCHER_ARG))
            })
        }

        factory {
            SwitcherInteractor(get())
        }

        factory { (args: SwitcherArguments) ->
            SwitcherStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args
            ).create()
        }
    }
}