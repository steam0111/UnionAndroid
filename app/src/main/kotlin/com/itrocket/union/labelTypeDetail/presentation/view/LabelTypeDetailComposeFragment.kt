package com.itrocket.union.labelTypeDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.union.labelTypeDetail.LabelTypeDetailModule.LABELTYPEDETAIL_VIEW_MODEL_QUALIFIER
import com.itrocket.union.labelTypeDetail.presentation.store.LabelTypeDetailStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import androidx.navigation.fragment.navArgs
import com.itrocket.union.labelTypeDetail.presentation.view.LabelTypeDetailComposeFragmentArgs

class LabelTypeDetailComposeFragment :
    BaseComposeFragment<LabelTypeDetailStore.Intent, LabelTypeDetailStore.State, LabelTypeDetailStore.Label>(
        LABELTYPEDETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<LabelTypeDetailComposeFragmentArgs>()

    override fun renderState(
        state: LabelTypeDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            LabelTypeDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(LabelTypeDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}