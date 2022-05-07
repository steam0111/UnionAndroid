package com.itrocket.union.filter.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filter.presentation.view.FilterComposeFragment.Companion.FILTER_ARG
import com.itrocket.union.filterValues.presentation.store.FilterValueArguments
import com.itrocket.union.filterValues.presentation.view.FilterValueComposeFragment

interface FilterStore : Store<FilterStore.Intent, FilterStore.State, FilterStore.Label> {

    sealed class Intent {
        data class OnFieldClicked(val filter: FilterDomain) : Intent()
        data class OnFilterChanged(val filter: FilterDomain) : Intent()
        object OnShowClicked : Intent()
        object OnCrossClicked : Intent()
        object OnDropClicked : Intent()
    }

    data class State(
        val filterFields: List<FilterDomain>,
        val resultCount: Int = 0
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class ShowFilterValues(val filter: FilterDomain) : Label(), ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(FILTER_ARG to FilterValueArguments(filter))
            override val containerId: Int
                get() = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = FilterValueComposeFragment()
        }
    }
}