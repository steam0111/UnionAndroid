package com.itrocket.union.producer

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.producer.data.ProducerRepositoryImpl
import com.itrocket.union.producer.domain.ProducerInteractor
import com.itrocket.union.producer.domain.dependencies.ProducerRepository
import com.itrocket.union.producer.presentation.store.ProducerStore
import com.itrocket.union.producer.presentation.store.ProducerStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ProducerModule {
    val PRODUCER_VIEW_MODEL_QUALIFIER = named("PRODUCER_VIEW_MODEL")

    val module = module {
        viewModel(PRODUCER_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<ProducerStore>())
        }

        factory<ProducerRepository> {
            ProducerRepositoryImpl(get(), get())
        }

        factory {
            ProducerInteractor(get(), get())
        }

        factory {
            ProducerStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get()
            ).create()
        }
    }
}