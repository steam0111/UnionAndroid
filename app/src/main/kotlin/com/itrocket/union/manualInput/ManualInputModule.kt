package com.itrocket.union.manualInput

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.manualInput.presentation.store.ManualInputArguments
import com.itrocket.union.manualInput.presentation.store.ManualInputStore
import com.itrocket.union.manualInput.presentation.store.ManualInputStoreFactory
import com.itrocket.union.manualInput.presentation.view.ManualInputComposeFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ManualInputModule {
    val MANUALINPUT_VIEW_MODEL_QUALIFIER = named("MANUALINPUT_VIEW_MODEL")

    val module = module {
        viewModel(MANUALINPUT_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<ManualInputStore>() {
                parametersOf(args.getParcelable<ManualInputArguments>(ManualInputComposeFragment.MANUAL_INPUT_ARGS))
            })
        }

        factory { (args: ManualInputArguments) ->
            ManualInputStoreFactory(
                DefaultStoreFactory,
                get(),
                args
            ).create()
        }
    }
}