package com.itrocket.union.bottomActionMenu.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
                onCreateDocClickListener = {
                    accept(BottomActionMenuStore.Intent.OnCreateDocClicked)
                },
                onDeleteItemClickListener = {
                    accept(
                        BottomActionMenuStore.Intent.OnDeleteItemClicked(
                            it
                        )
                    )
                },
                onOpenItemClickListener = { accept(BottomActionMenuStore.Intent.OnOpenItemClicked(it)) }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        Toast.makeText(requireContext(), "Запускаем нижнее меню кнопок", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val BOTTOMACTIONMENU_ARGS = "bottom action menu args"
    }
}