package com.itrocket.union.conterpartyDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.conterpartyDetail.CounterpartyDetailModule
import com.itrocket.union.conterpartyDetail.presentation.store.CounterpartyDetailStore

class CounterpartyDetailComposeFragment :
    BaseComposeFragment<CounterpartyDetailStore.Intent, CounterpartyDetailStore.State, CounterpartyDetailStore.Label>(
        CounterpartyDetailModule.COUNTERPARTY_DETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<CounterpartyDetailComposeFragmentArgs>()

    override fun renderState(
        state: CounterpartyDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            CounterpartyDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(CounterpartyDetailStore.Intent.OnBackClicked)
                }
            )
        }
    }
}