package com.itrocket.union.filterValues.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filterValues.presentation.view.FilterValueComposeFragment

interface FilterValueStore :
    Store<FilterValueStore.Intent, FilterValueStore.State, FilterValueStore.Label> {

    sealed class Intent {
        data class OnValueClicked(val filterValue: String) : Intent()
        data class OnSingleValueChanged(val value: String) : Intent()
        object OnDropClicked : Intent()
        object OnAcceptClicked : Intent()
        object OnCrossClicked : Intent()
        object OnCancelClicked : Intent()
    }

    data class State(
        val filter: FilterDomain,
        val filterValues: List<String> = listOf(),
        val singleValue: String = ""
    )

    sealed class Label {
        data class GoBack(override val result: FilterDomain?) : Label(),
            GoBackDialogNavigationLabel {
            override val resultCode = FilterValueComposeFragment.FILTER_VALUE_RESULT_CODE
            override val resultLabel = FilterValueComposeFragment.FILTER_RESULT
        }
    }
}