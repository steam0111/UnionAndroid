package com.itrocket.union.inventoryCreate

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.inventoryCreate.domain.InventoryCreateInteractor
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateStore
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateArguments
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateComposeFragment

object InventoryCreateModule {
    val INVENTORYCREATE_VIEW_MODEL_QUALIFIER = named("INVENTORYCREATE_VIEW_MODEL")

    val module = module {
        viewModel(INVENTORYCREATE_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<InventoryCreateStore> {
                parametersOf(
                    args.getParcelable<InventoryCreateArguments>(
                        InventoryCreateComposeFragment.INVENTORY_CREATE_ARGUMENTS
                    )
                )
            })
        }

        factory {
            InventoryCreateInteractor(get(), get(), get())
        }

        factory { (args: InventoryCreateArguments) ->
            InventoryCreateStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args,
                get()
            ).create()
        }
    }
}