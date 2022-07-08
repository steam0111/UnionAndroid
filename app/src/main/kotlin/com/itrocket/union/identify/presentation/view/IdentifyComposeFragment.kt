package com.itrocket.union.identify.presentation.view

import android.util.Log
import androidx.compose.ui.platform.ComposeView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuResult
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment.Companion.BOTTOM_ACTION_RESULT_CODE
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment.Companion.BOTTOM_ACTION_RESULT_LABEL
import com.itrocket.union.chooseAction.presentation.store.ChooseActionResult
import com.itrocket.union.documents.presentation.store.DocumentStore
import com.itrocket.union.identify.IdentifyModule.IDENTIFY_VIEW_MODEL_QUALIFIER
import com.itrocket.union.identify.presentation.store.IdentifyStore

class IdentifyComposeFragment :
    BaseComposeFragment<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label>(
        IDENTIFY_VIEW_MODEL_QUALIFIER
    ) {
    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = BOTTOM_ACTION_RESULT_CODE,
                resultLabel = BOTTOM_ACTION_RESULT_LABEL,
                resultAction =
                { objectAction ->
                    (objectAction as BottomActionMenuResult?)?.type?.let {
                        accept(IdentifyStore.Intent.OnObjectActionSelected(it))
                    }
                }
            )
        )

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
                onObjectClickListener = {
                    accept(IdentifyStore.Intent.OnReservesClicked(it))
                    Log.d("SukhanovTest", "Reserves item click in Identify " + it.title)
                },
//                onOSClickListener = { accept(IdentifyStore.Intent.OnOSClicked(it)) },
                onDropClickListener = {
                    accept(IdentifyStore.Intent.OnDropClicked)
                },
                onPageChanged = {
                    accept(IdentifyStore.Intent.OnSelectPage(it))
                }
            )
        }
    }
}