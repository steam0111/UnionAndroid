package com.itrocket.union.filter.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.filter.FilterModule.FILTER_VIEW_MODEL_QUALIFIER
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filter.presentation.store.FilterStore
import com.itrocket.union.filterValues.presentation.view.FilterValueComposeFragment
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.location.presentation.view.LocationComposeFragment

class FilterComposeFragment :
    BaseComposeFragment<FilterStore.Intent, FilterStore.State, FilterStore.Label>(
        FILTER_VIEW_MODEL_QUALIFIER
    ) {

    override val navArgs by navArgs<FilterComposeFragmentArgs>()

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = FilterValueComposeFragment.FILTER_VALUE_RESULT_CODE,
                resultLabel = FilterValueComposeFragment.FILTER_RESULT,
                resultAction = {
                    accept(
                        FilterStore.Intent.OnFilterChanged(
                            it as FilterDomain? ?: return@FragmentResult
                        )
                    )
                }
            ),
            FragmentResult(
                resultCode = LocationComposeFragment.LOCATION_RESULT_CODE,
                resultLabel = LocationComposeFragment.LOCATION_RESULT,
                resultAction = {
                    accept(
                        FilterStore.Intent.OnFilterLocationChanged(
                            it as LocationResult? ?: return@FragmentResult
                        )
                    )
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
            })
        }
    }

    companion object {
        const val FILTER_ARG = "filter arg"
    }
}