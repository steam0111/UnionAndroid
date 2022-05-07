package com.itrocket.union.reserves.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.reserves.ReservesModule.RESERVES_VIEW_MODEL_QUALIFIER
import com.itrocket.union.reserves.presentation.store.ReservesStore

class ReservesComposeFragment :
    BaseComposeFragment<ReservesStore.Intent, ReservesStore.State, ReservesStore.Label>(
        RESERVES_VIEW_MODEL_QUALIFIER
    ) {

    override fun renderState(
        state: ReservesStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ReservesScreen(
                state = state,
                appInsets = appInsets,
                onSearchClickListener = {
                    accept(ReservesStore.Intent.OnSearchClicked)
                },
                onBackClickListener = {
                    accept(ReservesStore.Intent.OnBackClicked)
                },
                onFilterClickListener = {
                    accept(ReservesStore.Intent.OnFilterClicked)
                },
                onReservesListener = {
                    accept(ReservesStore.Intent.OnItemClicked(it))
                }

            )
        }
    }
}