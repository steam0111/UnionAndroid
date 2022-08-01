package com.itrocket.union.bottomActionMenu.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.bottomActionMenu.BottomActionMenuModule.BOTTOMACTIONMENU_VIEW_MODEL_QUALIFIER
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuStore

class BottomActionMenuFragment :
    BaseComposeBottomSheet<BottomActionMenuStore.Intent, BottomActionMenuStore.State, BottomActionMenuStore.Label>(
        BOTTOMACTIONMENU_VIEW_MODEL_QUALIFIER
    ) {
    override fun renderState(
        state: BottomActionMenuStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            BottomActionMenuScreen(
                state = state,
                appInsets = appInsets,
                onTypeClickListener = {
                    accept(
                        BottomActionMenuStore.Intent.OnTypeClicked(
                            it,
                            accountingObjectDomain = state.accountingObjectDomain,
                            accountingObjects = state.accountingObjects
                        )
                    )
                }
            )
        }
    }

    companion object {
        const val BOTTOM_ACTION_MENU_ARGS = "bottom action menu args"
        const val BOTTOM_ACTION_RESULT_CODE = "bottom action result code"
        const val BOTTOM_ACTION_RESULT_LABEL = "bottom action result label"
    }
}