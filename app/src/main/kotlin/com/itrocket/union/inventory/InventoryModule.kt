package com.itrocket.union.inventory

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.inventory.data.InventoryRepositoryImpl
import com.itrocket.union.inventory.domain.InventoryInteractor
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventory.presentation.store.InventoryStore
import com.itrocket.union.inventory.presentation.store.InventoryStoreFactory
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragmentArgs
import com.itrocket.core.base.BaseViewModel

object InventoryModule {
    val INVENTORY_VIEW_MODEL_QUALIFIER = named("INVENTORY_VIEW_MODEL")

    val module = module {
        viewModel(INVENTORY_VIEW_MODEL_QUALIFIER) { (args: InventoryComposeFragmentArgs) ->
            BaseViewModel(get<InventoryStore>() {
                parametersOf(args)
            })
        }

        factory<InventoryRepository> {
            InventoryRepositoryImpl(get())
        }

        factory {
            InventoryInteractor(get(), get())
        }

        factory { (args: InventoryComposeFragmentArgs) ->
            InventoryStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.inventoryArguments
            ).create()
        }
    }
}