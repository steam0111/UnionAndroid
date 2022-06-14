package com.itrocket.union.inventoryReserves

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import com.itrocket.union.inventoryReserves.data.InventoryReservesRepositoryImpl
import com.itrocket.union.inventoryReserves.domain.InventoryReservesInteractor
import com.itrocket.union.inventoryReserves.domain.dependencies.InventoryReservesRepository
import com.itrocket.union.inventoryReserves.presentation.store.InventoryReservesStore
import com.itrocket.union.inventoryReserves.presentation.store.InventoryReservesStoreFactory
import com.itrocket.union.inventoryReserves.presentation.view.InventoryReservesComposeFragmentArgs
import com.itrocket.core.base.BaseViewModel

object InventoryReservesModule {
    val INVENTORYRESERVES_VIEW_MODEL_QUALIFIER = named("INVENTORYRESERVES_VIEW_MODEL")

    val module = module {
        viewModel(INVENTORYRESERVES_VIEW_MODEL_QUALIFIER) { (args: InventoryReservesComposeFragmentArgs) ->
            BaseViewModel(get<InventoryReservesStore>() {
                parametersOf(args)
            })
        }

        factory<InventoryReservesRepository> {
            InventoryReservesRepositoryImpl(get())
        }

        factory {
            InventoryReservesInteractor(get(), get())
        }

        factory { (args: InventoryReservesComposeFragmentArgs) ->
            InventoryReservesStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.inventoryReservesArguments
            ).create()
        }
    }
}