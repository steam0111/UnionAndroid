package com.itrocket.union.dataCollect

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.dataCollect.domain.DataCollectInteractor
import com.itrocket.union.dataCollect.presentation.store.DataCollectStore
import com.itrocket.union.dataCollect.presentation.store.DataCollectStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DataCollectModule {
    val DATA_COLLECT_VIEW_MODEL_QUALIFIER = named("DATA_COLLECT_VIEW_MODEL")

    val module = module {
        viewModel(DATA_COLLECT_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<DataCollectStore>())
        }
        factory {
            DataCollectInteractor()
        }
        factory {
            DataCollectStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}