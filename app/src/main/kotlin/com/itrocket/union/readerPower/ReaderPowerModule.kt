package com.itrocket.union.readerPower

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.readerPower.data.ReaderPowerRepositoryImpl
import com.itrocket.union.readerPower.domain.ReaderPowerInteractor
import com.itrocket.union.readerPower.domain.dependencies.ReaderPowerRepository
import com.itrocket.union.readerPower.presentation.store.ReaderPowerStore
import com.itrocket.union.readerPower.presentation.store.ReaderPowerStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ReaderPowerModule {
    val READERPOWER_VIEW_MODEL_QUALIFIER = named("READERPOWER_VIEW_MODEL")

    val module = module {
        viewModel(READERPOWER_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<ReaderPowerStore>())
        }

        factory<ReaderPowerRepository> {
            ReaderPowerRepositoryImpl(get())
        }

        factory {
            ReaderPowerInteractor(get(), get(), get())
        }

        factory {
            ReaderPowerStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}