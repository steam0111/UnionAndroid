package com.itrocket.union.structural

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.structural.data.StructuralRepositoryImpl
import com.itrocket.union.structural.domain.StructuralInteractor
import com.itrocket.union.structural.domain.dependencies.StructuralRepository
import com.itrocket.union.structural.presentation.store.StructuralStore
import com.itrocket.union.structural.presentation.store.StructuralStoreFactory
import com.itrocket.union.structural.presentation.view.StructuralComposeFragmentArgs
import com.itrocket.core.base.BaseViewModel

object StructuralModule {
    val STRUCTURAL_VIEW_MODEL_QUALIFIER = named("STRUCTURAL_VIEW_MODEL")

    val module = module {
        viewModel(STRUCTURAL_VIEW_MODEL_QUALIFIER) { (args: StructuralComposeFragmentArgs) ->
            BaseViewModel(get<StructuralStore>() {
                parametersOf(args)
            })
        }

        factory<StructuralRepository> {
            StructuralRepositoryImpl(get(), get())
        }

        factory {
            StructuralInteractor(get(), get())
        }

        factory { (args: StructuralComposeFragmentArgs) ->
            StructuralStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.structuralArguments,
                get(),
                get()
            ).create()
        }
    }
}