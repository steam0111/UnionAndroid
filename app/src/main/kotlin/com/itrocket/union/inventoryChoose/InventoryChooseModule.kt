package com.itrocket.union.inventoryChoose

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.inventoryChoose.presentation.store.InventoryChooseArguments
import com.itrocket.union.inventoryChoose.presentation.store.InventoryChooseStore
import com.itrocket.union.inventoryChoose.presentation.store.InventoryChooseStoreFactory
import com.itrocket.union.inventoryChoose.presentation.view.InventoryChooseComposeFragment.Companion.INVENTORY_CHOOSE_ARGUMENT
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object InventoryChooseModule {
    val INVENTORYCHOOSE_VIEW_MODEL_QUALIFIER = named("INVENTORYCHOOSE_VIEW_MODEL_QUALIFIER")

    val module = module {
        viewModel(INVENTORYCHOOSE_VIEW_MODEL_QUALIFIER) { (argument: Bundle) ->
            BaseViewModel(get<InventoryChooseStore> {
                parametersOf(
                    argument.getParcelable<InventoryChooseArguments>(
                        INVENTORY_CHOOSE_ARGUMENT
                    )
                )
            })
        }

        factory { (arguments: InventoryChooseArguments) ->
            InventoryChooseStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                arguments,
            ).create()
        }
    }
}