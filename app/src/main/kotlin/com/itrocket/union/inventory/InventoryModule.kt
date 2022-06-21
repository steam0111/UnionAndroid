package com.itrocket.union.inventory

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.inventory.data.InventoryRepositoryImpl
import com.itrocket.union.inventory.domain.InventoryInteractor
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventory.presentation.store.InventoryStore
import com.itrocket.union.inventory.presentation.store.InventoryStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object InventoryModule {
    val INVENTORY_VIEW_MODEL_QUALIFIER = named("INVENTORY_VIEW_MODEL")

    val module = module {
        viewModel(INVENTORY_VIEW_MODEL_QUALIFIER) {
            BaseViewModel(get<InventoryStore>())
        }

        factory<InventoryRepository> {
            InventoryRepositoryImpl(get(), get())
        }

        factory {
            InventoryInteractor(get(), get())
        }

        factory {
            InventoryStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get()
            ).create()
        }
    }
}