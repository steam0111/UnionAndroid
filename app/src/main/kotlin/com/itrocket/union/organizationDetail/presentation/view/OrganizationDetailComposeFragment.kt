package com.itrocket.union.organizationDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.organizationDetail.OrganizationDetailModule
import com.itrocket.union.organizationDetail.presentation.store.OrganizationDetailStore

class OrganizationDetailComposeFragment :
    BaseComposeFragment<OrganizationDetailStore.Intent, OrganizationDetailStore.State, OrganizationDetailStore.Label>(
        OrganizationDetailModule.ORGANIZATION_DETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<OrganizationDetailComposeFragmentArgs>()

    override fun renderState(
        state: OrganizationDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            OrganizationDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(OrganizationDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}