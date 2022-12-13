package com.itrocket.union.documentsMenu

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.documentsMenu.data.DocumentMenuRepositoryImpl
import com.itrocket.union.documentsMenu.domain.DocumentMenuInteractor
import com.itrocket.union.documentsMenu.domain.dependencies.DocumentMenuRepository
import com.itrocket.union.documentsMenu.presentation.store.DocumentMenuStore
import com.itrocket.union.documentsMenu.presentation.store.DocumentMenuStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DocumentMenuModule {
    val DOCUMENTMENU_VIEW_MODEL_QUALIFIER = named("DOCUMENTMENU_VIEW_MODEL")

    val module = module {
        viewModel(DOCUMENTMENU_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<DocumentMenuStore>())
        }

        factory<DocumentMenuRepository> {
            DocumentMenuRepositoryImpl()
        }

        factory {
            DocumentMenuInteractor(get(), get(), get(), get(), get())
        }

        factory {
            DocumentMenuStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            ).create()
        }
    }
}