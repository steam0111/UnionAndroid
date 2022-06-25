package com.itrocket.union.regionDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.regionDetail.data.RegionDetailRepositoryImpl
import com.itrocket.union.regionDetail.domain.RegionDetailInteractor
import com.itrocket.union.regionDetail.domain.dependencies.RegionDetailRepository
import com.itrocket.union.regionDetail.presentation.store.RegionDetailStore
import com.itrocket.union.regionDetail.presentation.store.RegionDetailStoreFactory
import com.itrocket.union.regionDetail.presentation.view.RegionDetailComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object RegionDetailModule {
    val REGION_DETAIL_VIEW_MODEL_QUALIFIER = named("REGION_DETAIL_VIEW_MODEL")

    val module = module {
        viewModel(REGION_DETAIL_VIEW_MODEL_QUALIFIER) { (args: RegionDetailComposeFragmentArgs) ->
            BaseViewModel(get<RegionDetailStore>() {
                parametersOf(args)
            })
        }

        factory<RegionDetailRepository> {
            RegionDetailRepositoryImpl(get())
        }

        factory {
            RegionDetailInteractor(get(), get())
        }

        factory { (args: RegionDetailComposeFragmentArgs) ->
            RegionDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.regionDetailComposeFragmentArgs
            ).create()
        }
    }
}