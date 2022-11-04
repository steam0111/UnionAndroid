package com.itrocket.union.selectParams.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.selectParams.SelectParamsModule.SELECTPARAMS_VIEW_MODEL_QUALIFIER
import com.itrocket.union.selectParams.presentation.store.SelectParamsStore

class SelectParamsComposeFragment :
    BaseComposeFragment<SelectParamsStore.Intent, SelectParamsStore.State, SelectParamsStore.Label>(
        SELECTPARAMS_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<SelectParamsComposeFragmentArgs>()

    override fun renderState(
        state: SelectParamsStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            SelectParamsScreen(
                state = state,
                appInsets = appInsets,
                onCrossClickListener = {
                    accept(SelectParamsStore.Intent.OnCrossClicked)
                },
                onNextClickListener = {
                    accept(SelectParamsStore.Intent.OnNextClicked)
                },
                onPrevClickListener = {
                    accept(SelectParamsStore.Intent.OnPrevClicked)
                },
                onAcceptClickListener = {
                    accept(SelectParamsStore.Intent.OnAcceptClicked)
                },
                onItemSelected = {
                    accept(SelectParamsStore.Intent.OnCommonItemSelected(it))
                },
                onSearchTextChanged = {
                    accept(SelectParamsStore.Intent.OnSearchTextChanged(it))
                },
                onLocationBackClick = {
                    accept(SelectParamsStore.Intent.OnLocationBackClicked)
                },
                onStructuralBackClick = {
                    accept(SelectParamsStore.Intent.OnStructuralBackClicked)
                },
                onLocationSelected = {
                    accept(SelectParamsStore.Intent.OnLocationSelected(it))
                },
                onStructuralSelected = {
                    accept(SelectParamsStore.Intent.OnStructuralSelected(it))
                }
            )
        }
    }

    companion object {
        const val SELECT_PARAMS_RESULT_CODE = "select params result code"
        const val SELECT_PARAMS_RESULT = "select params result"
    }
}