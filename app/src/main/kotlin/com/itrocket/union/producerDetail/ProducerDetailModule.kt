package com.itrocket.union.producerDetail

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.producerDetail.data.ProducerDetailRepositoryImpl
import com.itrocket.union.producerDetail.domain.ProducerDetailInteractor
import com.itrocket.union.producerDetail.domain.dependencies.ProducerDetailRepository
import com.itrocket.union.producerDetail.presentation.store.ProducerDetailStore
import com.itrocket.union.producerDetail.presentation.store.ProducerDetailStoreFactory
import com.itrocket.union.producerDetail.presentation.view.ProducerDetailComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ProducerDetailModule {
    val PRODUCER_DETAIL_VIEW_MODEL_QUALIFIER =
        named("PRODUCER_DETAIL_VIEW_MODEL")

    val module = module {
        viewModel(PRODUCER_DETAIL_VIEW_MODEL_QUALIFIER) { (args: ProducerDetailComposeFragmentArgs) ->
            BaseViewModel(get<ProducerDetailStore>() {
                parametersOf(args)
            })
        }

        factory<ProducerDetailRepository> {
            ProducerDetailRepositoryImpl(get())
        }

        factory {
            ProducerDetailInteractor(get(), get())
        }

        factory { (args: ProducerDetailComposeFragmentArgs) ->
            ProducerDetailStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.producerDetailComposeFragmentArgs
            ).create()
        }
    }
}