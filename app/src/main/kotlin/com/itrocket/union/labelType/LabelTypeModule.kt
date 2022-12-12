package com.itrocket.union.labelType

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.itrocket.union.labelType.data.LabelTypeRepositoryImpl
import com.itrocket.union.labelType.domain.LabelTypeInteractor
import com.itrocket.union.labelType.domain.dependencies.LabelTypeRepository
import com.itrocket.union.labelType.presentation.store.LabelTypeStore
import com.itrocket.union.labelType.presentation.store.LabelTypeStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.labelType.presentation.view.LabelTypeComposeFragmentArgs
import org.koin.core.parameter.parametersOf

object LabelTypeModule {
    val LABELTYPE_VIEW_MODEL_QUALIFIER = named("LABELTYPE_VIEW_MODEL")

    val module = module {
        viewModel(LABELTYPE_VIEW_MODEL_QUALIFIER) { (argument: LabelTypeComposeFragmentArgs) ->
            BaseViewModel(get<LabelTypeStore> {
                parametersOf(argument)
            })
        }

        factory<LabelTypeRepository> {
            LabelTypeRepositoryImpl(get(), get())
        }

        factory {
            LabelTypeInteractor(get(), get())
        }

        factory { (argument: LabelTypeComposeFragmentArgs) ->
            LabelTypeStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get(),
                argument.labelTypeArguments
            ).create()
        }
    }
}