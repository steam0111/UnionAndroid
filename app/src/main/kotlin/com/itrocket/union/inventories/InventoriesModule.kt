package com.itrocket.union.inventories

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.inventories.domain.InventoriesInteractor
import com.itrocket.union.inventories.presentation.store.InventoriesStore
import com.itrocket.union.inventories.presentation.store.InventoriesStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object InventoriesModule {
    val INVENTORIES_VIEW_MODEL_QUALIFIER = named("INVENTORIES_VIEW_MODEL")

    val module = module {
        viewModel(INVENTORIES_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<InventoriesStore>())
        }

        factory {
            InventoriesInteractor(get(), get())
        }

        factory {
            InventoriesStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get()
            ).create()
        }
    }
}