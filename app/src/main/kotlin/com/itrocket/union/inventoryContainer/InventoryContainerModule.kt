package com.itrocket.union.inventoryContainer

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.inventoryContainer.presentation.store.InventoryContainerStore
import com.itrocket.union.inventoryContainer.presentation.store.InventoryContainerStoreFactory
import com.itrocket.union.inventoryContainer.presentation.view.InventoryContainerComposeFragmentArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object InventoryContainerModule {
    val INVENTORYCONTAINER_VIEW_MODEL_QUALIFIER = named("INVENTORYCONTAINER_VIEW_MODEL")

    val module = module {
        viewModel(INVENTORYCONTAINER_VIEW_MODEL_QUALIFIER) { (args: InventoryContainerComposeFragmentArgs) ->
            BaseViewModel(get<InventoryContainerStore>() {
                parametersOf(args)
            })
        }

        factory { (args: InventoryContainerComposeFragmentArgs) ->
            InventoryContainerStoreFactory(
                DefaultStoreFactory,
                get(),
                args.inventoryContainerArguments
            ).create()
        }
    }
}