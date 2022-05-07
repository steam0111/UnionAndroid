package com.itrocket.union.filterValues.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.filterValues.FilterValueModule.FILTERVALUE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.filterValues.presentation.store.FilterValueStore

class FilterValueComposeFragment :
    BaseComposeBottomSheet<FilterValueStore.Intent, FilterValueStore.State, FilterValueStore.Label>(
        FILTERVALUE_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    @OptIn(ExperimentalPagerApi::class)
    override fun renderState(
        state: FilterValueStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            FilterValueScreen(
                state = state,
                appInsets = appInsets,
                onDropClickListener = {
                    accept(FilterValueStore.Intent.OnDropClicked)
                },
                onApplyClickListener = {
                    accept(FilterValueStore.Intent.OnAcceptClicked)
                },
                onValueSelected = {
                    accept(FilterValueStore.Intent.OnValueClicked(it))
                },
                onCrossClickListener = {
                    accept(FilterValueStore.Intent.OnCrossClicked)
                },
                onSingleValueChanged = {
                    accept(FilterValueStore.Intent.OnSingleValueChanged(it))
                },
                onCloseClickListener = {
                    accept(FilterValueStore.Intent.OnCancelClicked)
                }
            )
        }
    }

    companion object {
        const val FILTER_VALUE_RESULT_CODE = "filter value result code"
        const val FILTER_RESULT = "filter result"
        const val FILTER_ARG = "filter arg"
    }
}