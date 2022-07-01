package com.itrocket.union.bottomActionMenu

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuArguments
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuStore
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuStoreFactory
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment.Companion.BOTTOMACTIONMENU_ARGS
import com.itrocket.union.readingMode.presentation.store.ReadingModeStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf

object BottomActionMenuModule {
    val BOTTOMACTIONMENU_VIEW_MODEL_QUALIFIER = named("BOTTOMACTIONMENU_VIEW_MODEL_QUALIFIER")

    val module = module {
        viewModel(BOTTOMACTIONMENU_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<BottomActionMenuStore>() {
                parametersOf(args.getParcelable<BottomActionMenuArguments>(BOTTOMACTIONMENU_ARGS))
            })
        }

        factory { (args: BottomActionMenuArguments) ->
            BottomActionMenuStoreFactory(
                DefaultStoreFactory,
                get(),
                args,
            ).create()
        }
    }
}