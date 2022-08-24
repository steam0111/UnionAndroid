package com.itrocket.union.inventory

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.inventory.data.InventoryRepositoryImpl
import com.itrocket.union.inventory.domain.InventoryInteractor
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventory.presentation.store.InventoryArguments
import com.itrocket.union.inventory.presentation.store.InventoryStore
import com.itrocket.union.inventory.presentation.store.InventoryStoreFactory
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateArguments
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateComposeFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object InventoryModule {
    val INVENTORY_VIEW_MODEL_QUALIFIER = named("INVENTORY_VIEW_MODEL")

    val module = module {
        viewModel(INVENTORY_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<InventoryStore> {
                parametersOf(
                    args.getParcelable<InventoryArguments>(
                        InventoryComposeFragment.INVENTORY_ARGUMENT
                    )
                )
            })
        }

        factory<InventoryRepository> {
            InventoryRepositoryImpl(get(), get())
        }

        factory {
            InventoryInteractor(get(), get(), get(), get())
        }

        factory { (arguments : InventoryArguments)->
            InventoryStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                arguments.inventoryCreateDomain,
                get()
            ).create()
        }
    }
}