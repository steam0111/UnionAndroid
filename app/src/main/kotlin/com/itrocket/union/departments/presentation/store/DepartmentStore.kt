package com.itrocket.union.departments.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.departments.domain.entity.DepartmentDomain

interface DepartmentStore :
    Store<DepartmentStore.Intent, DepartmentStore.State, DepartmentStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnDepartmentClicked(val id: String) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val departments: List<DepartmentDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}