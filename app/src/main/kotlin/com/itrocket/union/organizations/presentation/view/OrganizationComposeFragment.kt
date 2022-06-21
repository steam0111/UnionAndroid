package com.itrocket.union.organizations.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.organizations.OrganizationModule.ORGANIZATION_VIEW_MODEL_QUALIFIER
import com.itrocket.union.organizations.presentation.store.OrganizationStore

class OrganizationComposeFragment :
    BaseComposeFragment<OrganizationStore.Intent, OrganizationStore.State, OrganizationStore.Label>(
        ORGANIZATION_VIEW_MODEL_QUALIFIER
    ) {
    override fun renderState(
        state: OrganizationStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            OrganizationScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = { accept(OrganizationStore.Intent.OnBackClicked) },
                onOrganizationClickListener = {}
            )
        }
    }
}