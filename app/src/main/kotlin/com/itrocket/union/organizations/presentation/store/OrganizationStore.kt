package com.itrocket.union.organizations.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.organizations.domain.entity.OrganizationDomain

interface OrganizationStore :
    Store<OrganizationStore.Intent, OrganizationStore.State, OrganizationStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnOrganizationsClicked(val id: String) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val organizations: List<OrganizationDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}