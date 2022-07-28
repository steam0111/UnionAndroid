package com.itrocket.union.organizationDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.organizationDetail.domain.entity.OrganizationDetailDomain

interface OrganizationDetailStore :
    Store<OrganizationDetailStore.Intent, OrganizationDetailStore.State, OrganizationDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val item: OrganizationDetailDomain = OrganizationDetailDomain(id = "", name = ""),
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(),
            DefaultNavigationErrorLabel
    }
}