package com.itrocket.union.producerDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.producerDetail.ProducerDetailModule
import com.itrocket.union.producerDetail.presentation.store.ProducerDetailStore

class ProducerDetailComposeFragment :
    BaseComposeFragment<ProducerDetailStore.Intent, ProducerDetailStore.State, ProducerDetailStore.Label>(
        ProducerDetailModule.PRODUCER_DETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<ProducerDetailComposeFragmentArgs>()

    override fun renderState(
        state: ProducerDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ProducerDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(ProducerDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}