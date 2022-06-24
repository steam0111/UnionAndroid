package com.itrocket.union.organizations.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragmentDirections
import com.itrocket.union.organizationDetail.presentation.store.OrganizationDetailArguments
import com.itrocket.union.organizations.domain.entity.OrganizationDomain
import com.itrocket.union.organizations.presentation.view.OrganizationComposeFragmentDirections

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
        class ShowDetail(val id: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = OrganizationComposeFragmentDirections.toOrganizationDetail(
                    OrganizationDetailArguments(id = id)
                )
        }
    }
}