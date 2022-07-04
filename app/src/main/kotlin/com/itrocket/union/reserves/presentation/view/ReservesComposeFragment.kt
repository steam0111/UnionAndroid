package com.itrocket.union.reserves.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.filter.presentation.view.FilterComposeFragment
import com.itrocket.union.reserves.ReservesModule.RESERVES_VIEW_MODEL_QUALIFIER
import com.itrocket.union.reserves.presentation.store.ReservesStore
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult

class ReservesComposeFragment :
    BaseComposeFragment<ReservesStore.Intent, ReservesStore.State, ReservesStore.Label>(
        RESERVES_VIEW_MODEL_QUALIFIER
    ) {

    override val navArgs: NavArgs by navArgs<ReservesComposeFragmentArgs>()

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(ReservesStore.Intent.OnBackClicked)
            }
        }
    }

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = FilterComposeFragment.FILTER_RESULT_CODE,
                resultLabel = FilterComposeFragment.FILTER_RESULT_LABEL,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(ReservesStore.Intent.OnFilterResult(it))
                    }
                }
            )
        )

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
                },
                onSearchTextChanged = {
                    accept(ReservesStore.Intent.OnSearchTextChanged(it))
                }
            )
        }
    }

    companion object {
        const val RESERVES_RESULT_LABEL = "reserves result"
        const val RESERVES_RESULT_CODE = "reserves result code"
    }
}