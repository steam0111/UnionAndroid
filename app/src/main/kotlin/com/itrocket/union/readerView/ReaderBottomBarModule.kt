package com.itrocket.union.readerView

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.union.readerView.presentation.store.ReaderBottomBarStore
import com.itrocket.union.readerView.presentation.store.ReaderBottomBarStoreFactory
import com.itrocket.union.readerView.presentation.store.ReaderBottomBarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ReaderBottomBarModule {
    val READER_BOTTOM_BAR_VIEW_MODEL_QUALIFIER = named("READER_BOTTOM_BAR_VIEW_MODEL")

    val module = module {
        viewModel(READER_BOTTOM_BAR_VIEW_MODEL_QUALIFIER) {
            ReaderBottomBarViewModel(get<ReaderBottomBarStore>())
        }

        factory {
            ReaderBottomBarStoreFactory(
                DefaultStoreFactory,
                get(),
                get()
            ).create()
        }
    }
}