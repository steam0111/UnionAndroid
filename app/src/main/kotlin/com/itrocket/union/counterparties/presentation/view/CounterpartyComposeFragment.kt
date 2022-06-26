package com.itrocket.union.counterparties.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.counterparties.CounterpartyModule.COUNTERPARTY_VIEW_MODEL_QUALIFIER
import com.itrocket.union.counterparties.presentation.store.CounterpartyStore
import com.itrocket.union.inventories.presentation.store.InventoriesStore

class CounterpartyComposeFragment :
    BaseComposeFragment<CounterpartyStore.Intent, CounterpartyStore.State, CounterpartyStore.Label>(
        COUNTERPARTY_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(CounterpartyStore.Intent.OnBackClicked)
            }
        }
    }

    override fun renderState(
        state: CounterpartyStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            CounterpartyScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(CounterpartyStore.Intent.OnBackClicked)
                },
                onCounterpartyClickListener = {
                    accept(CounterpartyStore.Intent.OnCounterpartyClicked(it.id))
                },
                onSearchTextChanged = {
                    accept(CounterpartyStore.Intent.OnSearchTextChanged(it))
                },
                onSearchClickListener = {
                    accept(CounterpartyStore.Intent.OnSearchClicked)
                },
            )
        }
    }
}