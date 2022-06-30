package com.itrocket.union.identify.presentation.view

import android.util.Log
import androidx.compose.ui.platform.ComposeView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.identify.IdentifyModule.IDENTIFY_VIEW_MODEL_QUALIFIER
import com.itrocket.union.identify.presentation.store.IdentifyStore

class IdentifyComposeFragment :
    BaseComposeFragment<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label>(
        IDENTIFY_VIEW_MODEL_QUALIFIER
    ) {
    @OptIn(ExperimentalPagerApi::class)
    override fun renderState(
        state: IdentifyStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            IdentifyScreen(
                state = state,
                appInsets = appInsets,
                onReadingModeClickListener = {
                    accept(IdentifyStore.Intent.OnReadingModeClicked)
                },
                onBackClickListener = {
                    accept(IdentifyStore.Intent.OnBackClicked)
                },
                onSaveClickListener = {
                    accept(IdentifyStore.Intent.OnSaveClicked)
                },
                onOSClickListener = { accept(IdentifyStore.Intent.OnReadingModeClicked) },
//                onOSClickListener = { accept(IdentifyStore.Intent.OnOSClicked(it)) },
                onDropClickListener = {
                    accept(IdentifyStore.Intent.OnDropClicked)
                },
                onPageChanged = {
                    accept(IdentifyStore.Intent.OnSelectPage(it))
                },
                onReservesClickListener = { accept(IdentifyStore.Intent.OnReservesClicked(it))
                Log.d("SukhanovTest", "Reserves item click in Identify "+ it.title)
                })
        }
    }
}