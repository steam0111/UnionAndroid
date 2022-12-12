package com.itrocket.union.labelType.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.itrocket.union.labelType.LabelTypeModule.LABELTYPE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.labelType.presentation.store.LabelTypeStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import com.itrocket.union.labelType.presentation.store.LabelTypeArguments

class LabelTypeComposeFragment :
    BaseComposeFragment<LabelTypeStore.Intent, LabelTypeStore.State, LabelTypeStore.Label>(
        LABELTYPE_VIEW_MODEL_QUALIFIER
    ) {

    override val navArgs: NavArgs by navArgs<LabelTypeComposeFragmentArgs>()

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

    companion object {
        const val LABEL_TYPE_RESULT_CODE = "LABEL_TYPE_RESULT_CODE"
        const val LABEL_TYPE_RESULT_LABEL = "LABEL_TYPE_RESULT_LABEL"
    }
}