package com.itrocket.union.structural.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.structural.domain.entity.StructuralDomain
import com.itrocket.union.structural.presentation.view.StructuralComposeFragment

interface StructuralStore :
    Store<StructuralStore.Intent, StructuralStore.State, StructuralStore.Label> {

    sealed class Intent {
        data class OnStructuralSelected(val structural: StructuralDomain) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        object OnBackClicked : Intent()
        object OnCrossClicked : Intent()
        object OnAcceptClicked : Intent()
        object OnFinishClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val selectStructuralScheme: List<StructuralDomain> = listOf(),
        val searchText: String = "",
        val levelHint: String = "",
        val structuralValues: List<StructuralDomain> = listOf()
    )

    sealed class Label {
        data class GoBack(override val result: StructuralResult? = null) : Label(), GoBackNavigationLabel {
            override val resultCode: String
                get() = StructuralComposeFragment.STRUCTURAL_RESULT_CODE

            override val resultLabel: String
                get() = StructuralComposeFragment.STRUCTURAL_RESULT
        }
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}