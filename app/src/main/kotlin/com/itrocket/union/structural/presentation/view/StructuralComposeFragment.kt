package com.itrocket.union.structural.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.union.structural.StructuralModule.STRUCTURAL_VIEW_MODEL_QUALIFIER
import com.itrocket.union.structural.presentation.store.StructuralStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import androidx.navigation.fragment.navArgs

class StructuralComposeFragment :
    BaseComposeFragment<StructuralStore.Intent, StructuralStore.State, StructuralStore.Label>(
        STRUCTURAL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<StructuralComposeFragmentArgs>()

    override fun renderState(
        state: StructuralStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            StructuralScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(StructuralStore.Intent.OnBackClicked)
                },
                onFinishClickListener = {
                    accept(StructuralStore.Intent.OnFinishClicked)
                },
                onAcceptClickListener = {
                    accept(StructuralStore.Intent.OnAcceptClicked)
                },
                onCrossClickListener = {
                    accept(StructuralStore.Intent.OnCrossClicked)
                },
                onStructuralSelected = {
                    accept(StructuralStore.Intent.OnStructuralSelected(it))
                },
                onSearchTextChanged = {
                    accept(StructuralStore.Intent.OnSearchTextChanged(it))
                }
            )
        }
    }

    companion object {
        const val STRUCTURAL_RESULT_CODE = "structural result code"
        const val STRUCTURAL_RESULT = "structural result"
    }
}