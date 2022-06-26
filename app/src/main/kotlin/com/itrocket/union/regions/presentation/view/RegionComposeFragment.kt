package com.itrocket.union.regions.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.regions.RegionModule.REGION_VIEW_MODEL_QUALIFIER
import com.itrocket.union.regions.presentation.store.RegionStore

class RegionComposeFragment :
    BaseComposeFragment<RegionStore.Intent, RegionStore.State, RegionStore.Label>(
        REGION_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(RegionStore.Intent.OnBackClicked)
            }
        }
    }

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
                    accept(RegionStore.Intent.OnRegionClicked(it.id))
                },
                onSearchClickListener = {
                    accept(RegionStore.Intent.OnSearchClicked)
                },
                onSearchTextChanged = {
                    accept(RegionStore.Intent.OnSearchTextChanged(it))
                }
            )
        }
    }
}