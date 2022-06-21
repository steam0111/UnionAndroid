package com.itrocket.union.regions.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.regions.RegionModule.REGION_VIEW_MODEL_QUALIFIER
import com.itrocket.union.regions.presentation.store.RegionStore

class RegionComposeFragment :
    BaseComposeFragment<RegionStore.Intent, RegionStore.State, RegionStore.Label>(
        REGION_VIEW_MODEL_QUALIFIER
    ) {

    override fun renderState(
        state: RegionStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            RegionScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(RegionStore.Intent.OnBackClicked)
                },
                onRegionClickListener = {
                    accept(RegionStore.Intent.OnRegionClicked(it))
                }
            )
        }
    }
}