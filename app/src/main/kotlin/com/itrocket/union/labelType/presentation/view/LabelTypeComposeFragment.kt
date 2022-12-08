package com.itrocket.union.labelType.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import com.itrocket.union.labelType.LabelTypeModule.LABELTYPE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.labelType.presentation.store.LabelTypeStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets

class LabelTypeComposeFragment :
    BaseComposeFragment<LabelTypeStore.Intent, LabelTypeStore.State, LabelTypeStore.Label>(
        LABELTYPE_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(LabelTypeStore.Intent.OnBackClicked)
            }
        }
    }

    override fun renderState(
        state: LabelTypeStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            LabelTypeScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(LabelTypeStore.Intent.OnBackClicked)
                },
                onItemClickListener = {
                    accept(LabelTypeStore.Intent.OnItemClicked(it.id))
                },
                onSearchClickListener = {
                    accept(LabelTypeStore.Intent.OnSearchClicked)
                },
                onSearchTextChanged = {
                    accept(LabelTypeStore.Intent.OnSearchTextChanged(it))
                },
                onLoadNext = {
                    accept(LabelTypeStore.Intent.OnLoadNext)
                }
            )
        }
    }
}