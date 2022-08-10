package com.itrocket.union.organizations.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.inventories.presentation.store.InventoriesStore
import com.itrocket.union.organizations.OrganizationModule.ORGANIZATION_VIEW_MODEL_QUALIFIER
import com.itrocket.union.organizations.presentation.store.OrganizationStore

class OrganizationComposeFragment :
    BaseComposeFragment<OrganizationStore.Intent, OrganizationStore.State, OrganizationStore.Label>(
        ORGANIZATION_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(OrganizationStore.Intent.OnBackClicked)
            }
        }
    }

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
                onOrganizationClickListener = {
                    accept(
                        OrganizationStore.Intent.OnOrganizationsClicked(it.id)
                    )
                },
                onSearchTextChanged = {
                    accept(OrganizationStore.Intent.OnSearchTextChanged(it))
                },
                onSearchClickListener = {
                    accept(OrganizationStore.Intent.OnSearchClicked)
                },
            )
        }
    }
}