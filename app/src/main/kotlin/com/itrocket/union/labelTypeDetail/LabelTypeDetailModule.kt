package com.itrocket.union.labelTypeDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.labelTypeDetail.data.LabelTypeDetailRepositoryImpl
import com.itrocket.union.labelTypeDetail.domain.LabelTypeDetailInteractor
import com.itrocket.union.labelTypeDetail.domain.dependencies.LabelTypeDetailRepository
import com.itrocket.union.labelTypeDetail.presentation.store.LabelTypeDetailStore
import com.itrocket.union.labelTypeDetail.presentation.store.LabelTypeDetailStoreFactory
import com.itrocket.union.labelTypeDetail.presentation.view.LabelTypeDetailComposeFragmentArgs
import com.itrocket.core.base.BaseViewModel

object LabelTypeDetailModule {
    val LABELTYPEDETAIL_VIEW_MODEL_QUALIFIER = named("LABELTYPEDETAIL_VIEW_MODEL")

    val module = module {
        viewModel(LABELTYPEDETAIL_VIEW_MODEL_QUALIFIER) { (args: LabelTypeDetailComposeFragmentArgs) ->
            BaseViewModel(get<LabelTypeDetailStore>() {
                parametersOf(args)
            })
        }

        factory<LabelTypeDetailRepository> {
            LabelTypeDetailRepositoryImpl(get(), get())
        }

        factory {
            LabelTypeDetailInteractor(get(), get())
        }

        factory { (args: LabelTypeDetailComposeFragmentArgs) ->
            LabelTypeDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.labelTypeDetailArguments,
                get()
            ).create()
        }
    }
}