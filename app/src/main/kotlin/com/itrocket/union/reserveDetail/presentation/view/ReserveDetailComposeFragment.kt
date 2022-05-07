package com.itrocket.union.reserveDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.union.reserveDetail.ReserveDetailModule.RESERVEDETAIL_VIEW_MODEL_QUALIFIER
import com.itrocket.union.reserveDetail.presentation.store.ReserveDetailStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import androidx.navigation.fragment.navArgs
import com.itrocket.union.reserveDetail.presentation.view.ReserveDetailComposeFragmentArgs

class ReserveDetailComposeFragment :
    BaseComposeFragment<ReserveDetailStore.Intent, ReserveDetailStore.State, ReserveDetailStore.Label>(
        RESERVEDETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<ReserveDetailComposeFragmentArgs>()

    override fun renderState(
        state: ReserveDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ReserveDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(ReserveDetailStore.Intent.OnBackClicked)
                },
                onReadingModeClickListener = {
                    accept(ReserveDetailStore.Intent.OnReadingModeClicked)
                },
                onDocumentSearchClickListener = {
                    accept(ReserveDetailStore.Intent.OnDocumentSearchClicked)
                },
                onDocumentAddClickListener = {
                    accept(ReserveDetailStore.Intent.OnDocumentAddClicked)
                },
            )
        }
    }
}