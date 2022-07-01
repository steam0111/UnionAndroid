package com.itrocket.union.reserveDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.reserveDetail.data.ReserveDetailRepositoryImpl
import com.itrocket.union.reserveDetail.domain.ReserveDetailInteractor
import com.itrocket.union.reserveDetail.domain.dependencies.ReserveDetailRepository
import com.itrocket.union.reserveDetail.presentation.store.ReserveDetailStore
import com.itrocket.union.reserveDetail.presentation.store.ReserveDetailStoreFactory
import com.itrocket.union.reserveDetail.presentation.view.ReserveDetailComposeFragmentArgs
import com.itrocket.core.base.BaseViewModel

object ReserveDetailModule {
    val RESERVEDETAIL_VIEW_MODEL_QUALIFIER = named("RESERVEDETAIL_VIEW_MODEL")

    val module = module {
        viewModel(RESERVEDETAIL_VIEW_MODEL_QUALIFIER) { (args: ReserveDetailComposeFragmentArgs) ->
            BaseViewModel(get<ReserveDetailStore>() {
                parametersOf(args)
            })
        }

        factory<ReserveDetailRepository> {
            ReserveDetailRepositoryImpl()
        }

        factory {
            ReserveDetailInteractor(get(), get())
        }

        factory { (args: ReserveDetailComposeFragmentArgs) ->
            ReserveDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.reserveDetailComposeFragmentArgs,
                get()
            ).create()
        }
    }
}