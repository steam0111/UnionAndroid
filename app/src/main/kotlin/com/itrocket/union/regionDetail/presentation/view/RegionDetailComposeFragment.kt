package com.itrocket.union.regionDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.regionDetail.RegionDetailModule
import com.itrocket.union.regionDetail.presentation.store.RegionDetailStore

class RegionDetailComposeFragment :
    BaseComposeFragment<RegionDetailStore.Intent, RegionDetailStore.State, RegionDetailStore.Label>(
        RegionDetailModule.REGION_DETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<RegionDetailComposeFragmentArgs>()

    override fun renderState(
        state: RegionDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            RegionDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(RegionDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}