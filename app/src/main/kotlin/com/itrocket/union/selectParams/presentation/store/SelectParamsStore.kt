package com.itrocket.union.selectParams.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.location.domain.entity.LocationDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.selectParams.presentation.view.SelectParamsComposeFragment
import com.itrocket.union.structural.domain.entity.StructuralDomain

interface SelectParamsStore :
    Store<SelectParamsStore.Intent, SelectParamsStore.State, SelectParamsStore.Label> {

    sealed class Intent {
        data class OnCommonItemSelected(val item: ParamDomain) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        object OnCrossClicked : Intent()
        object OnAcceptClicked : Intent()
        object OnNextClicked : Intent()
        object OnPrevClicked : Intent()
        object OnLocationBackClicked : Intent()
        object OnStructuralBackClicked : Intent()
        data class OnLocationSelected(val location: LocationDomain) : Intent()
        data class OnStructuralSelected(val structural: StructuralDomain) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val currentParam: ParamDomain,
        val allParams: List<ParamDomain> = emptyList(),
        val searchText: String = "",
        val currentStep: Int,

        val commonParamValues: List<ParamDomain> = listOf(),
        val locationValues: List<LocationDomain> = listOf(),
        val structuralValues: List<StructuralDomain> = listOf(),

        val levelHint: String = "",
        val isLevelHintShow: Boolean = false,
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