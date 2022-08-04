package com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.selectActionWithValuesBottomMenu.SelectActionWithValuesBottomMenuModule.SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_VIEW_MODEL_QUALIFIER
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store.SelectActionWithValuesBottomMenuStore

class SelectActionWithValuesBottomMenuFragment :
    BaseComposeBottomSheet<SelectActionWithValuesBottomMenuStore.Intent, SelectActionWithValuesBottomMenuStore.State, SelectActionWithValuesBottomMenuStore.Label>(
        SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_VIEW_MODEL_QUALIFIER
    ) {
    override fun renderState(
        state: SelectActionWithValuesBottomMenuStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            SelectActionWithValuesBottomMenuScreen(
                state = state,
                appInsets = appInsets,
                onTypeClickListener = {
                    accept(
                        SelectActionWithValuesBottomMenuStore.Intent.OnTypeClicked(
                            it,
                            accountingObject = state.accountingObject,
                            accountingObjects = state.accountingObjects
                        )
                    )
                }
            )
        }
    }

    companion object {
        const val SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_ARGS =
            "select action with values bottom menu args"
        const val SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_CODE =
            "select action with values bottom menu result code"
        const val SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_LABEL =
            "select action with values bottom menu result label"
    }
}