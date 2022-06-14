package com.itrocket.union.employees.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.employees.domain.entity.EmployeeDomain

interface EmployeeStore : Store<EmployeeStore.Intent, EmployeeStore.State, EmployeeStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnEmployeeClicked(val id: String) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val employees: List<EmployeeDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}