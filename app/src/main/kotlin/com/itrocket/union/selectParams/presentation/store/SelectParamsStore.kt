package com.itrocket.union.selectParams.presentation.store

import androidx.compose.ui.text.input.TextFieldValue
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.selectParams.presentation.view.SelectParamsComposeFragment

interface SelectParamsStore :
    Store<SelectParamsStore.Intent, SelectParamsStore.State, SelectParamsStore.Label> {

    sealed class Intent {
        data class OnItemSelected(val item: ParamDomain) : Intent()
        data class OnSearchTextChanged(val searchText: TextFieldValue) : Intent()
        object OnCrossClicked : Intent()
        object OnAcceptClicked : Intent()
        object OnNextClicked : Intent()
        object OnPrevClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val currentStep: Int,
        val params: List<ParamDomain>,
        val currentParamValues: List<ParamDomain> = listOf(),
        val searchText: TextFieldValue = TextFieldValue(),
    )

    sealed class Label {
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class GoBack(
            override val result: SelectParamsResult? = null
        ) : Label(), GoBackNavigationLabel {

            override val resultCode: String = SelectParamsComposeFragment.SELECT_PARAMS_RESULT_CODE
            override val resultLabel: String = SelectParamsComposeFragment.SELECT_PARAMS_RESULT
        }
    }
}