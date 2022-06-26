package com.itrocket.union.branches.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.branchDetail.presentation.store.BranchDetailArguments
import com.itrocket.union.branches.domain.entity.BranchesDomain
import com.itrocket.union.branches.presentation.view.BranchesComposeFragmentDirections
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.manual.ParamDomain

interface BranchesStore : Store<BranchesStore.Intent, BranchesStore.State, BranchesStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnBranchClicked(val id: String) : Intent()
        class OnFilterResult(val params: List<ParamDomain>) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
    }

    data class State(
        val branches: List<BranchesDomain> = listOf(),
        val isLoading: Boolean = false,
        val isShowSearch: Boolean = false,
        val searchText: String = ""
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowDetail(val id: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = BranchesComposeFragmentDirections.toBranchDetail(
                    BranchDetailArguments(id = id)
                )
        }

        data class ShowFilter(val filters: List<ParamDomain>) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = BranchesComposeFragmentDirections.toFilter(FilterArguments(filters))
        }
    }
}