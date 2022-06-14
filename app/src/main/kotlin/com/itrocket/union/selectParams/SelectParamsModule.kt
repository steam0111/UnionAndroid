package com.itrocket.union.selectParams

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.itrocket.union.selectParams.data.SelectParamsRepositoryImpl
import com.itrocket.union.selectParams.domain.SelectParamsInteractor
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import com.itrocket.union.selectParams.presentation.store.SelectParamsStore
import com.itrocket.union.selectParams.presentation.store.SelectParamsStoreFactory
import com.itrocket.union.selectParams.presentation.view.SelectParamsComposeFragmentArgs

object SelectParamsModule {
    val SELECTPARAMS_VIEW_MODEL_QUALIFIER = named("SELECTPARAMS_VIEW_MODEL")

    val module = module {
        viewModel(SELECTPARAMS_VIEW_MODEL_QUALIFIER) { (args: SelectParamsComposeFragmentArgs) ->
            BaseViewModel(get<SelectParamsStore> {
                parametersOf(args)
            })
        }

        factory<SelectParamsRepository> {
            SelectParamsRepositoryImpl(get(), get())
        }

        factory {
            SelectParamsInteractor(get(), get())
        }

        factory { (args: SelectParamsComposeFragmentArgs) ->
            SelectParamsStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.selectParamsComposeFragmentArgs
            ).create()
        }
    }
}