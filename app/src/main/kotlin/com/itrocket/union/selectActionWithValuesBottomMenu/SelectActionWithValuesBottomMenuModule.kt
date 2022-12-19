package com.itrocket.union.selectActionWithValuesBottomMenu

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store.SelectActionWithValuesBottomMenuArguments
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store.SelectActionWithValuesBottomMenuStore
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store.SelectActionWithValuesBottomMenuStoreFactory
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view.SelectActionWithValuesBottomMenuFragment.Companion.SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_ARGS
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SelectActionWithValuesBottomMenuModule {
    val SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_VIEW_MODEL_QUALIFIER =
        named("SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_VIEW_MODEL_QUALIFIER")

    val module = module {
        viewModel(SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<SelectActionWithValuesBottomMenuStore>() {
                parametersOf(
                    args.getParcelable(
                        SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_ARGS
                    )
                )
            })
        }
        factory { (args: SelectActionWithValuesBottomMenuArguments) ->
            SelectActionWithValuesBottomMenuStoreFactory(
                DefaultStoreFactory,
                get(),
                args,
                get(),
            ).create()
        }
    }
}