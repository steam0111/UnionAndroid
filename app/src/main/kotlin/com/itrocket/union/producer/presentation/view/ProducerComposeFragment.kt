package com.itrocket.union.producer.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.inventories.presentation.store.InventoriesStore
import com.itrocket.union.producer.ProducerModule.PRODUCER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.producer.presentation.store.ProducerStore

class ProducerComposeFragment :
    BaseComposeFragment<ProducerStore.Intent, ProducerStore.State, ProducerStore.Label>(
        PRODUCER_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(ProducerStore.Intent.OnBackClicked)
            }
        }
    }

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
                    accept(ProducerStore.Intent.OnProducerClicked(it.id))
                },
                onSearchClickListener = {
                    accept(ProducerStore.Intent.OnSearchClicked)
                },
                onSearchTextChanged = {
                    accept(ProducerStore.Intent.OnSearchTextChanged(it))
                },
            )
        }
    }
}