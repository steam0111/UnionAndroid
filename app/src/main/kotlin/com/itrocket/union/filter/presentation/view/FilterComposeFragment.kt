package com.itrocket.union.filter.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.filter.FilterModule.FILTER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.filter.presentation.store.FilterStore
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult
import com.itrocket.union.selectParams.presentation.view.SelectParamsComposeFragment

class FilterComposeFragment :
    BaseComposeFragment<FilterStore.Intent, FilterStore.State, FilterStore.Label>(
        FILTER_VIEW_MODEL_QUALIFIER
    ) {

    override val navArgs by navArgs<FilterComposeFragmentArgs>()

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = SelectParamsComposeFragment.SELECT_PARAMS_RESULT_CODE,
                resultLabel = SelectParamsComposeFragment.SELECT_PARAMS_RESULT,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(
                            FilterStore.Intent.OnFilterChanged(it)
                        )
                    }
                }
            )
        )

    override fun renderState(
        state: FilterStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            FilterScreen(state = state, appInsets = appInsets, onBackClickListener = {
                accept(FilterStore.Intent.OnCrossClicked)
            }, onDropClickListener = {
                accept(FilterStore.Intent.OnDropClicked)
            }, onFieldClickListener = { filter ->
                accept(FilterStore.Intent.OnFieldClicked(filter))
            }, onShowClickListener = {
                accept(FilterStore.Intent.OnShowClicked)
            }, onShowUtilizedClick = {
                accept(FilterStore.Intent.OnShowUtilizedClick(it))
            })
        }
    }

    companion object {
        const val FILTER_ARG = "filter arg"
        const val FILTER_RESULT_CODE = "filter result code"
        const val FILTER_RESULT_LABEL = "filter result label"
    }
}