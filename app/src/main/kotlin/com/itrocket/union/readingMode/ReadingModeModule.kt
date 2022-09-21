package com.itrocket.union.readingMode

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.readingMode.data.ReadingModeRepositoryImpl
import com.itrocket.union.readingMode.domain.ReadingModeInteractor
import com.itrocket.union.readingMode.domain.dependencies.ReadingModeRepository
import com.itrocket.union.readingMode.presentation.store.ReadingModeStore
import com.itrocket.union.readingMode.presentation.store.ReadingModeStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ReadingModeModule {
    val READINGMODE_VIEW_MODEL_QUALIFIER = named("READINGMODE_VIEW_MODEL")

    val module = module {
        viewModel(READINGMODE_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<ReadingModeStore>())
        }

        factory<ReadingModeRepository> {
            ReadingModeRepositoryImpl()
        }

        factory {
            ReadingModeInteractor(get(), get(), get())
        }

        factory {
            ReadingModeStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get()
            ).create()
        }
    }
}