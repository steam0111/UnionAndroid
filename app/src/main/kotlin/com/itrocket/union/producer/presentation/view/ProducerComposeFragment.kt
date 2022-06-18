package com.itrocket.union.producer.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.producer.ProducerModule.PRODUCER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.producer.presentation.store.ProducerStore

class ProducerComposeFragment :
    BaseComposeFragment<ProducerStore.Intent, ProducerStore.State, ProducerStore.Label>(
        PRODUCER_VIEW_MODEL_QUALIFIER
    ) {

    override fun renderState(
        state: ProducerStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            ProducerScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(ProducerStore.Intent.OnBackClicked)
                },
                onProducerClickListener = {
                    accept(ProducerStore.Intent.OnProducerClicked(it))
                }
            )
        }
    }
}